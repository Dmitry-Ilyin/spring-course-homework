package org.ilyin.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.ilyin.service.entities.Course;
import org.ilyin.service.entities.Department;
import org.ilyin.service.entities.Employee;
import org.ilyin.service.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
//@ActiveProfiles("test")
public class DbTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        Department departmentNew = new Department(null, "IT");
        Course courseNew = new Course(null, "Rest service");
        Employee employeeNew = new Employee(null, "Slave One", "last name",
                LocalDate.now(), departmentNew, 500, List.of(courseNew), 1L);

        em.persist(departmentNew);
        em.persist(courseNew);
        em.persist(employeeNew);

        em.flush();
    }

    @Test
    void test() {
        Assert.assertEquals(1, em.createQuery("FROM Department").getResultList().size());
        Employee employee = em.createQuery("FROM Employee", Employee.class)
                .setMaxResults(1)
                .getResultList()
                .get(0);

        Assert.assertEquals("Rest service", employee.getCourses().get(0).getName());
    }

}
