package org.ilyin.service.repository;

import org.ilyin.service.entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
//    Optional<Employee> findByFirstName(String firstName);
//    List<Employee> findAllByFirstName(String firstName);

    Optional<Employee> findByFirstName(String firstName);
    List<Employee> findAllByFirstName(String firstName);
}
