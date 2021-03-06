package pl.itcrowd.tutorial.hibernate.starter;

import pl.itcrowd.tutorial.hibernate.domain.Address;
import pl.itcrowd.tutorial.hibernate.domain.Company;
import pl.itcrowd.tutorial.hibernate.domain.Employee;
import pl.itcrowd.tutorial.hibernate.domain.Department;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

@Singleton
@Startup
public class Starter {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(Starter.class.getCanonicalName());

    @PostConstruct
    private void onCreate(){

        Address address = new Address();
        address.setCity("Tarnow");
        address.setStreet("Narutowicza");
        address.setZipCode("33-100");

        Company company = new Company();
        company.setName("ITCrowd");
        company.setAddress(address);


        Department department1 = new Department();
        department1.setName("D1");
        department1.setCompany(company);
        department1.setAddress(address);
        Department department2 = new Department();
        department2.setName("D2");
        department2.setCompany(company);
        department2.setAddress(address);

        Employee employee3 = new Employee();
        employee3.setName("M. Smith");
        employee3.setAddress(address);
        employee3.setDepartment(department2);
        Employee employee1 = new Employee();
        employee1.setName("J. Kowalski");
        employee1.setAddress(address);
        employee1.setDepartment(department1);
        employee1.setBoss(employee3);
        Employee employee2 = new Employee();
        employee2.setName("W. Nowak");
        employee2.setAddress(address);
        employee2.setDepartment(department1);
        employee2.setBoss(employee3);

        em.persist(company);
        em.persist(department1);
        em.persist(department2);
        em.persist(employee1);
        em.persist(employee2);
        em.persist(employee3);

        makeExampleQueries();
    }

    private void makeExampleQueries(){
        List<Employee> result = em.createNamedQuery("findAllBosses").getResultList();
        LOGGER.info("Result size:"+result.size()+", Result content:"+result.toString());

        //The same query as above, using typed query
        List<Employee> bossesTyped = em.createQuery("SELECT e FROM Employee e WHERE e.boss IS NULL").getResultList();
        LOGGER.info("Result size:"+bossesTyped.size()+", Result content:"+bossesTyped.toString());

        //Parametrized query
        List<Employee> onlySmith = em.createQuery("SELECT e FROM Employee e WHERE e.name LIKE :empName").setParameter("empName", "Smith").getResultList();
        LOGGER.info("Result size:"+onlySmith.size()+", Result content:"+onlySmith.toString());

        //Update query
        Query updQuery = em.createQuery("UPDATE Employee e SET e.boss = NULL WHERE e.name LIKE 'Kowalski'");
        updQuery.executeUpdate();

        //Query with IN and subquery
        List<Employee> employeesInDeptCity = em.createQuery("SELECT e FROM Employee e " +
                "WHERE e.address.city IN(" +
                "SELECT DISTINCT d.address.city FROM Department d WHERE d.company.name LIKE 'ITCrowd')")
                .getResultList();
        LOGGER.info("Result size:"+employeesInDeptCity.size()+", Result content:"+employeesInDeptCity.toString());

        //DISTINCT
        List<String> cityList = em.createQuery("SELECT DISTINCT e.address.city FROM Employee e").getResultList();
        LOGGER.info("Result size:"+cityList.size()+", Result content:"+cityList.toString());


        //different than
        List<String> cities = em.createQuery("SELECT e.address.city FROM Employee e WHERE e.address.city <> 'Tarnow'").getResultList();
        LOGGER.info("Result size:"+cities.size()+", Result content:"+cities.toString());


    }

}
