package pro.sky.animalshelterbot.entity;

import pro.sky.animalshelterbot.constant.ReportStatus;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс Report, представляет сущность отчета
 * @author Gubina Marina
 */
public class Report {

    /**
     * Поле: идентификационный номер отчета
     */
    private Long id;
    /**
     * Поле: фото в отчете
     */
    private byte[] photo;
    /**
     * Поле: рацион собаки
     */
    private String animalDiet;
    /**
     * Поле: общая информация
     */
    private String generalInfo;
    /**
     * Поле: изменения в поведении
     */
    private String changeBehavior;
    /**
     * Поле: статус отчета
     * @see ReportStatus
     */
    private ReportStatus reportStatus;

    public Report(byte[] photo, String animalDiet, String generalInfo, String changeBehavior) {
        this.photo = photo;
        this.animalDiet = animalDiet;
        this.generalInfo = generalInfo;
        this.changeBehavior = changeBehavior;
        this.reportStatus = ReportStatus.REPORT_POSTED;
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
                ", photo=" + Arrays.toString(photo) +
                ", animalDiet='" + animalDiet + '\'' +
                ", generalInfo='" + generalInfo + '\'' +
                ", changeBehavior='" + changeBehavior + '\'' +
                ", reportStatus=" + reportStatus +
                '}';
    }
}
