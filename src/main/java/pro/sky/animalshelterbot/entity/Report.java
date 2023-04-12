package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.ReportStatus;

import javax.persistence.*;
import java.time.LocalDate;

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

//    /**
//     * Поле: фото в отчете
//     */
//    @Lob
//    @Column(name = "photo",nullable = false)
//    private byte[] photo;

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

    private LocalDate dateMessage;

    /**
     * Поле: статус отчета
     * @see ReportStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus reportStatus;



    public Report(String animalDiet, String generalInfo, String changeBehavior, ReportStatus reportStatus) {
        this.animalDiet = animalDiet;
        this.generalInfo = generalInfo;
        this.changeBehavior = changeBehavior;
        this.reportStatus = ReportStatus.REPORT_POSTED;
    }

    public Report(Long id, Long chatId, String animalDiet, String generalInfo, String changeBehavior, LocalDate dateMessage, ReportStatus reportStatus) {
        this.id = id;
        this.chatId = chatId;
        this.animalDiet = animalDiet;
        this.generalInfo = generalInfo;
        this.changeBehavior = changeBehavior;
        this.dateMessage = dateMessage;
        this.reportStatus = ReportStatus.REPORT_POSTED;
    }

    public Report(){
    }

    public Long getId() {
        return id;
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

    public void setAnimalDiet(String animalDiet) {
        this.animalDiet = animalDiet;
    }

    public void setGeneralInfo(String generalInfo) {
        this.generalInfo = generalInfo;
    }

    public void setChangeBehavior(String changeBehavior) {
        this.changeBehavior = changeBehavior;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public LocalDate getDateMessage() {
        return dateMessage;
    }

    public void setLocalDate(LocalDate dateMessage) {
        this.dateMessage = dateMessage;
    }



    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", animalDiet='" + animalDiet + '\'' +
                ", generalInfo='" + generalInfo + '\'' +
                ", changeBehavior='" + changeBehavior + '\'' +
                ", reportStatus=" + reportStatus +
                '}';
    }
}
