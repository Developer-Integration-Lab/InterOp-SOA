package net.aegis.lab.web.action.admin;

import java.sql.Timestamp;
import java.util.Calendar;

import net.aegis.lab.web.action.BaseAction;

import org.apache.commons.validator.routines.CalendarValidator;

/**
 *
 * @author naresh.jaganathan
 */
public class ManageNewsUpdate extends BaseAction {

//    private Participant participant;
    private String doSearch;
    private String startDatetime;
    private Timestamp startTime;
    private String endDatetime;
    private Timestamp endTime;
   
    @Override
    public String execute() throws Exception {

        log.info("admin.ManageNewsUpdate.execute() - INFO");

        return SUCCESS;
    }

    public String getDoSearch() {
        return doSearch;
    }

    public void setDoSearch(String doSearch) {
        this.doSearch = doSearch;
    }

    public String getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
        try {
            CalendarValidator calValidator = CalendarValidator.getInstance();
            Calendar endCalendar = calValidator.validate(endDatetime, "yyyy-MM-dd HH:mm:ss");
            if (endCalendar != null) {
                endTime = Timestamp.valueOf(endDatetime);
            }
            else {
                endTime = null;
            }
        }
        catch (Exception e) {
            endTime = null;
        }
    }

    public String getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(String startDatetime) {
        this.startDatetime = startDatetime;
        try {
            CalendarValidator calValidator = CalendarValidator.getInstance();
            Calendar startCalendar = calValidator.validate(startDatetime, "yyyy-MM-dd HH:mm:ss");
            if (startCalendar != null) {
                startTime = Timestamp.valueOf(startDatetime);
            }
            else {
                startTime = null;
            }
        }
        catch (Exception e) {
            startTime = null;
        }
    }

 
    // ----- Private Helper Methods -----

    private boolean isInvalid(String value) {
        return (value == null || value.length() == 0);
    }

    private boolean validateCriteria() {
        boolean isValid = true;

        // Test for not null startTime and endTime
        if (isInvalid(startDatetime) || (!isInvalid(startDatetime) && startTime == null)) {
            this.addActionError("Please enter a valid Start Time format [yyyy-MM-dd HH:mm:ss]");
            isValid = false;
        }
        if (isInvalid(endDatetime) || (!isInvalid(endDatetime) && endTime == null)) {
            this.addActionError("Please enter a valid End Time format [yyyy-MM-dd HH:mm:ss]");
            isValid = false;
        }

        // Test for startTime < endTime
        if (isValid) {
            if (startTime.after(endTime)) {
                this.addActionError("Start Time cannot be after End Time");
                isValid = false;
            }
        }

        // Test for startTime to endTime not greater than ?? time
        if (isValid) {

        }

        return isValid;
    }
}
