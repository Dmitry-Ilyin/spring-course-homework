package org.ilyin.service.bussines;

import org.ilyin.service.dto.mapper.EmployeeMapper;
import org.ilyin.service.entities.Department;
import org.ilyin.service.entities.Employee;
import org.ilyin.service.repository.DepartmentRepository;
import org.ilyin.service.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkersService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper mapper;

    public Optional<org.ilyin.service.dto.Employee> getBossInDepartment(Long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isEmpty() || department.get().getEmployees().isEmpty()) {
            return Optional.empty();
        }

        Employee employee = department.get().getEmployees()
                .stream()
                .filter(i -> i.getBossId() == null)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Не найден id руководителя"));

        return Optional.of(mapper.toDto(employee));
    }

    public Optional<org.ilyin.service.dto.Employee> getBossOfEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        Long bossId = employee.get().getBossId();
        if (bossId == null) {
            try {
                throw new Exception("Это руководитель");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.of(mapper.toDto(employeeRepository.findById(bossId).get()));
    }

    public Iterable<org.ilyin.service.dto.Employee> getEmployeesMaxSalaryMoreThanBoss(Long departmentId) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isEmpty() || department.get().getEmployees().isEmpty()) {
            throw new NoSuchElementException("Нет отдела с таким id");
        }

        Employee employeeBoss = department.get().getEmployees()
                .stream()
                .filter(i -> i.getBossId() == null)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Не найден id руководителя"));

        List<org.ilyin.service.dto.Employee> employee = department.get().getEmployees()
                .stream()
                .filter(i -> !Objects.equals(i.getBossId(), employeeBoss.getBossId()) &&
                        i.getMonthSalary() > employeeBoss.getMonthSalary())
                .map(i -> mapper.toDto(i))
                .collect(Collectors.toList());
        return employee;
    }
}
