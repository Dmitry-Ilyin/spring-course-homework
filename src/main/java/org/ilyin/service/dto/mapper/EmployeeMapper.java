package org.ilyin.service.dto.mapper;


import org.ilyin.service.dto.Employee;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {
    Employee toDto(org.ilyin.service.entities.Employee employee);
}
