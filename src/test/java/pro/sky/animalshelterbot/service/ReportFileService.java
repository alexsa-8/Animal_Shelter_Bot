package pro.sky.animalshelterbot.service;

import com.pengrad.telegrambot.model.Document;
import com.pengrad.telegrambot.model.Message;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pro.sky.animalshelterbot.entity.BinaryContent;
import pro.sky.animalshelterbot.entity.ReportFile;
import pro.sky.animalshelterbot.repository.BinaryContentRepository;
import pro.sky.animalshelterbot.repository.ReportFileRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ReportFileService {
    @Value("${token}")
    private String token;
    @Value("${service.file_info.uri}")
    private String fileInfoUri;
    @Value("${service.file_storage.uri}")
    private String fileStorageUri;
    @Value("${link.address}")
    private String linkAddress;
    private final ReportFileRepository fileRepository;
    private final BinaryContentRepository binaryContentRepository;

    public ReportFileService(ReportFileRepository fileRepository, BinaryContentRepository binaryContentRepository) {
        this.fileRepository = fileRepository;
        this.binaryContentRepository = binaryContentRepository;
    }
    public ReportFile processDoc(Message telegramMessage) throws JSONException {
        Document telegramDoc = telegramMessage.document();
        String fileId = telegramDoc.fileId();
        ResponseEntity<String> response = getFilePath(fileId);
        if (response.getStatusCode() == HttpStatus.OK) {
            BinaryContent persistentBinaryContent = getPersistentBinaryContent(response);
            ReportFile reportFile = buildTransientDoc(telegramDoc, persistentBinaryContent);
            return fileRepository.save(reportFile);
        } else {
            throw new RuntimeException("Bad response from telegram service: " + response);
        }
    }

    private ResponseEntity<String> getFilePath(String fileId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                fileInfoUri,
                HttpMethod.GET,
                request,
                String.class,
                token, fileId
        );
    }
    private BinaryContent getPersistentBinaryContent(ResponseEntity<String> response) throws JSONException {
        String filePath = getFilePath(response);
        byte[] fileInByte = downloadFile(filePath);
        BinaryContent transientBinaryContent = new BinaryContent();
                transientBinaryContent.setFileOfByte(fileInByte);
        return binaryContentRepository.save(transientBinaryContent);
    }

    private String getFilePath(ResponseEntity<String> response) throws JSONException {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response.getBody());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(jsonObject
                .getJSONObject("result")
                .getString("file_path"));
    }


    private byte[] downloadFile(String filePath) {
        String fullUri = fileStorageUri.replace("{token}", token)
                .replace("{filePath}", filePath);
        URL urlObj = null;
        try {
            urlObj = new URL(fullUri);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        try (InputStream is = urlObj.openStream()) {
            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(urlObj.toExternalForm(), e);
        }
    }

    private ReportFile buildTransientDoc(Document telegramDoc, BinaryContent persistentBinaryContent) {
        ReportFile file = new ReportFile();
                file.setTelegramFileId(telegramDoc.fileId());
                file.setDocName(telegramDoc.fileName());
                file.setBinaryContent(persistentBinaryContent);
                file.setType(telegramDoc.mimeType());
                fileRepository.save(file);
           return file;
    }
}
