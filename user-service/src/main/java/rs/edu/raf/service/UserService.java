package rs.edu.raf.service;

import rs.edu.raf.dto.ChangePasswordDTO;
import rs.edu.raf.dto.ChangePasswordWithCodeDTO;
import rs.edu.raf.dto.LoginDto;

public interface UserService {

    /**
     * Log user into account
     * @param loginDto user credentials
     * @return JWT if login is successful
     */
    String loginUser(LoginDto loginDto);

    /**
     * Changes the password of a user.
     *
     * @param changePasswordDTO   The DTO containing information to change the password.
     * @return True if the password is changed
     */
    boolean changeUserPassword(ChangePasswordDTO changePasswordDTO);

    /**
     * Changes the password of a user using a code.
     *
     * @param changePasswordWithCodeDTO The DTO containing information to change the password.
     * @return True if password is changed
     */
    boolean changeUserPasswordWithCode(ChangePasswordWithCodeDTO changePasswordWithCodeDTO);
}
