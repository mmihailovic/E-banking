package rs.edu.raf.service;

import rs.edu.raf.dto.*;

public interface UserService {

    /**
     * Log user into account
     * @param loginDto user credentials
     * @return {@link LoginResponseDTO} if login is successful
     */
    LoginResponseDTO loginUser(LoginDto loginDto);

    /**
     * Gets the currently logged user
     * @return {@link LoggedUserDTO} object containing information
     * about currently logged user
     */
    LoggedUserDTO getLoggedUser();

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
