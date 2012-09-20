package net.aegis.lab.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import net.aegis.lab.dao.pojo.Patient;
import net.aegis.lab.genericdao.GenericDao;
/**
 * <p>Hibernate DAO layer for Patients</p>
 * <p>Generated at Sat Feb 06 09:46:37 EST 2010</p>
 *
 * @author Salto-db Generator Ant v1.0.15 / Pojos + Hibernate mapping + Generic DAO
 */
public interface PatientDAO extends GenericDao<Patient,String> {

	/*
	 * TODO : Add specific businesses daos here.
	 * These methods will be overwrited if you re-generate this interface.
	 * You might want to extend this interface and to change the dao factory to return 
	 * an instance of the new implemenation in buildPatientDAO()
	 */
	 
	/**
 	 * Find Patient by criteria.
 	 * If a parameter is null it is not used in the query.
	 */
	public List<Patient> findByCriteria(String firstName, String lastName, String addressLine1, String addressLine2, String city, String state, String zipCode, String homePhone, String workPhone, Timestamp dateOfBirth, String ssn, String gender);
	 
	/**
 	 * Find Patient by criteria.
	 */
	public List<Patient> findByCriteria(Map criterias);

	 
	/**
	 * Find Patient by firstName
	 */
	public List<Patient> findByFirstName(String firstName);

	/**
	 * Find Patient by lastName
	 */
	public List<Patient> findByLastName(String lastName);

	/**
	 * Find Patient by addressLine1
	 */
	public List<Patient> findByAddressLine1(String addressLine1);

	/**
	 * Find Patient by addressLine2
	 */
	public List<Patient> findByAddressLine2(String addressLine2);

	/**
	 * Find Patient by city
	 */
	public List<Patient> findByCity(String city);

	/**
	 * Find Patient by state
	 */
	public List<Patient> findByState(String state);

	/**
	 * Find Patient by zipCode
	 */
	public List<Patient> findByZipCode(String zipCode);

	/**
	 * Find Patient by homePhone
	 */
	public List<Patient> findByHomePhone(String homePhone);

	/**
	 * Find Patient by workPhone
	 */
	public List<Patient> findByWorkPhone(String workPhone);

	/**
	 * Find Patient by dateOfBirth
	 */
	public List<Patient> findByDateOfBirth(Timestamp dateOfBirth);

	/**
	 * Find Patient by ssn
	 */
	public List<Patient> findBySsn(String ssn);

	/**
	 * Find Patient by gender
	 */
	public List<Patient> findByGender(String gender);

}