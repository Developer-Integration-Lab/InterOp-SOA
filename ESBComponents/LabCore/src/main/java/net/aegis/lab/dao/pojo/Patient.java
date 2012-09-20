package net.aegis.lab.dao.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

import net.aegis.lab.util.Format;

/**
 * <p>Pojo mapping table patient</p>
 * <p></p>
 *
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 * @author Salto-db Generator Ant v1.0.15 / Hibernate pojos and xml mapping files.
 *
 */
public class Patient implements Serializable {

    /**
     * Attribute patientId.
     */
    private String patientId;
    /**
     * Attribute prefix.
     */
    private String prefix;
    /**
     * Attribute firstName.
     */
    private String firstName;
    /**
     * Attribute middleName.
     */
    private String middleName;
    /**
     * Attribute lastName.
     */
    private String lastName;
    /**
     * Attribute suffix.
     */
    private String suffix;
    /**
     * Attribute addressLine1.
     */
    private String addressLine1;
    /**
     * Attribute addressLine2.
     */
    private String addressLine2;
    /**
     * Attribute city.
     */
    private String city;
    /**
     * Attribute state.
     */
    private String state;
    /**
     * Attribute zipCode.
     */
    private String zipCode;
    /**
     * Attribute homePhone.
     */
    private String homePhone;
    /**
     * Attribute workPhone.
     */
    private String workPhone;
    /**
     * Attribute dateOfBirth.
     */
    private Timestamp dateOfBirth;
    /**
     * Attribute ssn.
     */
    private String ssn;
    /**
     * Attribute gender.
     */
    private String gender;
    /**
     * Attribute status.
     */
    private String status;

    // Helper properties
    private String dateOfBirthStr;

    /**
     * Used to differentiate between lab and conformance.
     * Story 51: Integrate Conformance Tests - Core Library changes
     */
    private Integer labAccessFilter;
    /**
     * @return patientId
     */
    public String getPatientId() {
        return patientId;
    }

    /**
     * @param patientId new value for patientId
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    /**
     * @return prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix new value for prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName new value for firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName new value for middleName
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName new value for lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix new value for suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * @param addressLine1 new value for addressLine1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * @return addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * @param addressLine2 new value for addressLine2
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city new value for city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state new value for state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode new value for zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return homePhone
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * @param homePhone new value for homePhone
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * @return workPhone
     */
    public String getWorkPhone() {
        return workPhone;
    }

    /**
     * @param workPhone new value for workPhone
     */
    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    /**
     * @return dateOfBirth
     */
    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth new value for dateOfBirth
     */
    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return ssn
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * @param ssn new value for ssn
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender new value for gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status new value for status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    // Helper properties
    /**
     * @return dateOfBirthStr
     */
    public String getDateOfBirthStr() {
        if (dateOfBirth != null) {
            dateOfBirthStr = Format.getFormattedDate(Format.MMDDYYYY_DATEFORMAT, dateOfBirth);
        }
        return dateOfBirthStr;
    }

    /**
     * @param dateOfBirthStr new value for dateOfBirthStr
     */
    public void setDateOfBirthStr(String dateOfBirthStr) {
        this.dateOfBirthStr = dateOfBirthStr;

        if (dateOfBirthStr != null) {
            this.dateOfBirth = Format.getTimestampInstance(Format.MMDDYYYY_DATEFORMAT, dateOfBirthStr);
        } else {
            this.dateOfBirth = null;
        }
    }

    public Integer getLabAccessFilter() {
        return labAccessFilter;
    }

    public void setLabAccessFilter(Integer labAccessFilter) {
        this.labAccessFilter = labAccessFilter;
    }
}
