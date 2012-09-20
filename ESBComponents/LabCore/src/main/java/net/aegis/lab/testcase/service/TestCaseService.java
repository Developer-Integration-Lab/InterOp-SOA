/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.aegis.lab.testcase.service;

import java.util.List;

import net.aegis.lab.dao.pojo.Testcase;
import net.aegis.lab.exception.ServiceException;

/**
 *
 * @author Jyoti.Devarakonda
 */
public interface TestCaseService {


     /*
     * Standard CRUD Methods
     */
    public Integer create(Testcase newInstance) throws ServiceException;

    public Testcase read(Integer id) throws ServiceException;

    public void update(Testcase transientObject) throws ServiceException;

    public void updateTestCaseAndScenarioCases(Testcase testCase) throws ServiceException;

    public void delete(Testcase persistentObject) throws ServiceException;

    public void deleteById(Integer id) throws ServiceException;

    public List<Testcase> findAll() throws ServiceException;

    public List<Testcase> findByName(String name) throws ServiceException;

}
