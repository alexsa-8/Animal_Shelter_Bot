package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.ReportStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Arrays;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Класс Report, представляет сущность отчета
 * @author Gubina Marina
 */
@Entity
@Table(name = "report")
public class Report {

    /**
     * Поле: идентификационный номер отчета
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Long chatId;
    private String filePath;
    private long fileSize;
    /**
     * Поле: фото в отчете
     */
    @Lob
    @Column(name = "photo",nullable = false)
    private byte[] photo;

    /**
     * Поле: рацион собаки
     */
    @Column(name = "animal_diet", nullable = false)
    private String animalDiet;

    /**
     * Поле: общая информация
     */
    @Column(name = "general_info",nullable = false)
    private String generalInfo;

    /**
     * Поле: изменения в поведении
     */
    @Column(name = "change_behavior",nullable = false)
    private String changeBehavior;

    private Date dateMessage;
    private long days;

    /**
     * Поле: статус отчета
     * @see ReportStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus reportStatus;

    public Report(String filePath, long fileSize, byte[] photo, String animalDiet, String generalInfo, String changeBehavior, ReportStatus reportStatus) {
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.photo = photo;
        this.animalDiet = animalDiet;
        this.generalInfo = generalInfo;
        this.changeBehavior = changeBehavior;
        this.reportStatus = ReportStatus.REPORT_POSTED;
    }

    public Report(Long chatId, String filePath, long fileSize, byte[] photo, String animalDiet,
                  String generalInfo, String changeBehavior, Date dataMessage, ReportStatus reportStatus) {
        this.chatId = chatId;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.photo = photo;
        this.animalDiet = animalDiet;
        this.generalInfo = generalInfo;
        this.changeBehavior = changeBehavior;
        this.dateMessage = dataMessage;
        this.reportStatus = ReportStatus.REPORT_POSTED;
    }

    public Report(){
    }

    public Long getChatId() {
        return chatId;
    }

    public String getFilePath() {
        return filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public Long getId() {
        return id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getAnimalDiet() {
        return animalDiet;
    }

    public String getGeneralInfo() {
        return generalInfo;
    }

    public String getChangeBehavior() {
        return changeBehavior;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setAnimalDiet(String animalDiet) {
        this.animalDiet = animalDiet;
    }

    public void setGeneralInfo(String generalInfo) {
        this.generalInfo = generalInfo;
    }

    public void setChangeBehavior(String changeBehavior) {
        this.changeBehavior = changeBehavior;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id) && Arrays.equals(photo, report.photo) && Objects.equals(animalDiet, report.animalDiet) && Objects.equals(generalInfo, report.generalInfo) && Objects.equals(changeBehavior, report.changeBehavior) && reportStatus == report.reportStatus;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, animalDiet, generalInfo, changeBehavior, reportStatus);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", photo=" + Arrays.toString(photo) +
                ", animalDiet='" + animalDiet + '\'' +
                ", generalInfo='" + generalInfo + '\'' +
                ", changeBehavior='" + changeBehavior + '\'' +
                ", dataMessage=" + dateMessage +
                ", reportStatus=" + reportStatus +
                '}';
    }
}
