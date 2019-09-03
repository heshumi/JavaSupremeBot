package authorization;

public class MyCallBack {
    private String $id;
    private String currentDateTime;
    private String utcOffset;
    private boolean isDayLightSavingsTime;
    private String dayOfTheWeek;
    private String timeZoneName;
    private String currentFileTime;
    private String ordinalDate;
    private String serviceResponse;

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public String getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(String utcOffset) {
        this.utcOffset = utcOffset;
    }

    public boolean isDayLightSavingsTime() {
        return isDayLightSavingsTime;
    }

    public void setDayLightSavingsTime(boolean dayLightSavingsTime) {
        isDayLightSavingsTime = dayLightSavingsTime;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getTimeZoneName() {
        return timeZoneName;
    }

    public void setTimeZoneName(String timeZoneName) {
        this.timeZoneName = timeZoneName;
    }

    public String getCurrentFileTime() {
        return currentFileTime;
    }

    public void setCurrentFileTime(String currentFileTime) {
        this.currentFileTime = currentFileTime;
    }

    public String getOrdinalDate() {
        return ordinalDate;
    }

    public void setOrdinalDate(String ordinalDate) {
        this.ordinalDate = ordinalDate;
    }

    public String getServiceResponse() {
        return serviceResponse;
    }

    public void setServiceResponse(String serviceResponse) {
        this.serviceResponse = serviceResponse;
    }
}
