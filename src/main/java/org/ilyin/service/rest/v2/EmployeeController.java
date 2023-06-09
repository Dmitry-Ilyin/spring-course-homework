package org.ilyin.service.rest.v2;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.ilyin.service.bussines.WorkersService;
import org.ilyin.service.entities.Employee;
import org.ilyin.service.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController("employee controller v2")
@RequestMapping("/v2/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private WorkersService workersService;

    @GetMapping
    Iterable<Employee> getAll(@RequestParam(required = false) String firstName) {
        if (firstName == null) {
            return repository.findAll();
        }
        return repository.findAllByFirstName(firstName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(operationId = "addEmployee", summary = "add new employee")
    Employee newEmployee(@RequestBody Employee employeeNew) {
        log.info("------------------------------------------");
        if (employeeNew.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Found Id. Use PUT instead of POST");
        }
        return repository.save(employeeNew);
    }

    @GetMapping("/{id}")
    Employee getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
