package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.ReportStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Класс ReportCat, представляет сущность отчета по котам
 * @author Gubina Marina
 */
@Entity
@Table(name = "report_cat")
public class ReportCat {

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
     * Поле: рацион кота
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
    @JoinColumn(name = "owner_cat_id")
    private OwnerCat ownerCat;

    public ReportCat(Long chatId, byte[] photo, String animalDiet,
                     String generalInfo, String changeBehavior, LocalDate date) {
        this.chatId = chatId;
        this.photo = photo;
        this.animalDiet = animalDiet;
        this.generalInfo = generalInfo;
        this.changeBehavior = changeBehavior;
        this.dateMessage = date;
        this.reportStatus = ReportStatus.REPORT_POSTED;
    }

    public ReportCat(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getAnimalDiet() {
        return animalDiet;
    }

    public void setAnimalDiet(String animalDiet) {
        this.animalDiet = animalDiet;
    }

    public String getGeneralInfo() {
        return generalInfo;
    }

    public void setGeneralInfo(String generalInfo) {
        this.generalInfo = generalInfo;
    }

    public String getChangeBehavior() {
        return changeBehavior;
    }

    public void setChangeBehavior(String changeBehavior) {
        this.changeBehavior = changeBehavior;
    }

    public LocalDate getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(LocalDate dateMessage) {
        this.dateMessage = dateMessage;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public OwnerCat getOwnerCat() {
        return ownerCat;
    }

    public void setOwnerCat(OwnerCat ownerCat) {
        this.ownerCat = ownerCat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportCat reportCat = (ReportCat) o;
        return Objects.equals(id, reportCat.id) && Objects.equals(chatId, reportCat.chatId) && Arrays.equals(photo, reportCat.photo) && Objects.equals(animalDiet, reportCat.animalDiet) && Objects.equals(generalInfo, reportCat.generalInfo) && Objects.equals(changeBehavior, reportCat.changeBehavior) && Objects.equals(dateMessage, reportCat.dateMessage) && reportStatus == reportCat.reportStatus && Objects.equals(ownerCat, reportCat.ownerCat);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, chatId, animalDiet, generalInfo, changeBehavior, dateMessage, reportStatus, ownerCat);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "ReportCat{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", photo=" + Arrays.toString(photo) +
                ", animalDiet='" + animalDiet + '\'' +
                ", generalInfo='" + generalInfo + '\'' +
                ", changeBehavior='" + changeBehavior + '\'' +
                ", dateMessage=" + dateMessage +
                ", reportStatus=" + reportStatus +
                ", ownerCat=" + ownerCat +
                '}';
    }
}
