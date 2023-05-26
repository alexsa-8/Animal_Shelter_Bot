package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.ReportStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Класс ReportDog, представляет сущность отчета по собакам
 * @author Gubina Marina
 */
@Entity
@Table(name = "report_dog")
public class ReportDog {

    /**
     * Поле: идентификационный номер отчета
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Поле: идентификационный номер чата
     */
    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    /**
     * Поле: фото в отчете
     */
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
    /**
     * Поле: дата отправки отчета
     */
    @Column(name = "date_message", nullable = false)
    private LocalDate dateMessage;

    /**
     * Поле: статус отчета
     * @see ReportStatus
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus reportStatus;

    @ManyToOne
    @JoinColumn(name = "owner_dog_id")
    private OwnerDog ownerDog;

    public ReportDog(Long chatId, byte[] photo, String animalDiet,
                     String generalInfo, String changeBehavior, LocalDate date) {
        this.chatId = chatId;
        this.photo = photo;
        this.animalDiet = animalDiet;
        this.generalInfo = generalInfo;
        this.changeBehavior = changeBehavior;
        this.dateMessage = date;
        this.reportStatus = ReportStatus.REPORT_POSTED;
    }

    public ReportDog(){

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

    public void setId(Long id) {
        this.id = id;
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

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
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

    public void setDateMessage(LocalDate dateMessage) {
        this.dateMessage = dateMessage;
    }

    public OwnerDog getOwnerDog() {
        return ownerDog;
    }

    public void setOwnerDog(OwnerDog ownerDog) {
        this.ownerDog = ownerDog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportDog reportDog = (ReportDog) o;
        return Objects.equals(id, reportDog.id) && Objects.equals(chatId, reportDog.chatId) && Arrays.equals(photo, reportDog.photo) && Objects.equals(animalDiet, reportDog.animalDiet) && Objects.equals(generalInfo, reportDog.generalInfo) && Objects.equals(changeBehavior, reportDog.changeBehavior) && Objects.equals(dateMessage, reportDog.dateMessage) && reportStatus == reportDog.reportStatus;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, chatId, animalDiet, generalInfo, changeBehavior, dateMessage, reportStatus);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "ReportDog{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", photo=" + Arrays.toString(photo) +
                ", animalDiet='" + animalDiet + '\'' +
                ", generalInfo='" + generalInfo + '\'' +
                ", changeBehavior='" + changeBehavior + '\'' +
                ", dateMessage=" + dateMessage +
                ", reportStatus=" + reportStatus +
                ", ownerDog=" + ownerDog +
                '}';
    }
}
