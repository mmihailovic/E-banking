package rs.edu.raf.mapper;

import org.springframework.stereotype.Component;
import rs.edu.raf.dto.LoggedUserDTO;
import rs.edu.raf.model.user.Employee;
import rs.edu.raf.model.user.Role;
import rs.edu.raf.model.user.User;

@Component
public class UserMapper {

    public LoggedUserDTO userToLoggedUserDTO(User user) {
        if(user instanceof Employee employee) {
            return new LoggedUserDTO(employee.getId(), employee.getUsername(), employee.getCompany().getId(), user.getRoles().stream().map(Role::getName).toList());
        }
        return new LoggedUserDTO(user.getId(), user.getUsername(), null, user.getRoles().stream().map(Role::getName).toList());
    }
}
