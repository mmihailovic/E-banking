package org.example.userservicekotlin.service

import org.example.userservicekotlin.dto.*

interface UserService {
    /**
     * Log user into account
     * @param loginDto user credentials
     * @return [LoginResponseDTO] if login is successful
     */
    fun loginUser(loginDto: LoginDTO): LoginResponseDTO

    /**
     * Gets the currently logged user
     * @return [LoggedUserDTO] object containing information
     * about currently logged user
     */
    fun getLoggedUser(): LoggedUserDTO

    /**
     * Changes the password of a user.
     *
     * @param changePasswordDTO   The DTO containing information to change the password.
     * @return True if the password is changed
     */
    fun changeUserPassword(changePasswordDTO: ChangePasswordDTO): Boolean

    /**
     * Changes the password of a user using a code.
     *
     * @param changePasswordWithCodeDTO The DTO containing information to change the password.
     * @return True if password is changed
     */
    fun changeUserPasswordWithCode(changePasswordWithCodeDTO: ChangePasswordWithCodeDTO): Boolean
}