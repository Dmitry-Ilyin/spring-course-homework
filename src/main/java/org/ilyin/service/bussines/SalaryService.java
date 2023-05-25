package org.ilyin.service.bussines;

import org.ilyin.service.dto.mapper.EmployeeMapper;
import org.ilyin.service.entities.Department;
import org.ilyin.service.entities.Employee;
import org.ilyin.service.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalaryService {

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    private EmployeeMapper mapper;

    public Optional<org.ilyin.service.dto.Employee> maxSalaryEmployeeInDepartment(Long departmentId) {
        Optional<Department> department = repository.findById(departmentId);
        if (department.isEmpty() || department.get().getEmployees().isEmpty()) {
            return Optional.empty();
        }

        Employee employee = department.get().getEmployees().stream()
                .max((e1, e2) -> e1.getMonthSalary().compareTo(e2.getMonthSalary())).get();

        return Optional.of(mapper.toDto(employee));
    }
}
