package org.ilyin.service.rest.v2;

import io.swagger.v3.oas.annotations.Operation;
import org.ilyin.service.bussines.WorkersService;
import org.ilyin.service.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v2/department/")
public class WorkersController {

    @Autowired
    private WorkersService workersService;

    @GetMapping("/boss/{departmentId}")
    @Operation(operationId = "BossInDepartment", summary = "Get Boss.")
    Employee getBossInDepartment(@PathVariable Long departmentId) {
        return workersService.getBossInDepartment(departmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Boss not found"));

    }

    @GetMapping("/boss/employee/{employeeId}")
    @Operation(operationId = "BossOfEmployee", summary = "Get Boss of employee.")
    org.ilyin.service.dto.Employee getBossOfEmployee(@PathVariable Long employeeId) {
        return workersService.getBossOfEmployee(employeeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "employees's boss not found"));

    }

    @GetMapping("/boss/employee/maxSalary/{departmentId}")
    @Operation(operationId = "EmployeesMaxSalaryMoreThanBoss", summary = "Get Employees max salary more than boss.")
    Iterable<org.ilyin.service.dto.Employee> getEmployeesMaxSalaryMoreThanBoss(@PathVariable Long departmentId) {
        return workersService.getEmployeesMaxSalaryMoreThanBoss(departmentId);
    }
}
