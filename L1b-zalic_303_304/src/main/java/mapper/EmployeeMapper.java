package mapper;

import com.lss.l1bzalic_303_304.dto.EmployeeDto;
import com.lss.l1bzalic_303_304.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDto toDto(Employee employee) {
        if (employee == null) return null;
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName() + " " + employee.getLastName(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getDepartment() != null ? employee.getDepartment().getName() : null
        );
    }
}

