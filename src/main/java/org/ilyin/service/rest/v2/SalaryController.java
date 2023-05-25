package org.ilyin.service.rest.v2;

import io.swagger.v3.oas.annotations.Operation;
import org.ilyin.service.bussines.SalaryService;
import org.ilyin.service.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/v2/salary/")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/max/depatment/{departmentId}")
    @Operation(operationId = "maxSalaryEmployeeInDep", summary = "Get max salary employee.")
    Employee maxSalaryEmployeeInDepartment(@PathVariable Long departmentId) {
        return salaryService.maxSalaryEmployeeInDepartment(departmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Department or employees not found"));
    }
}
