package org.example.userservicekotlin.service

import org.example.userservicekotlin.model.code.CodeType

interface CodeService {
    /**
     * Adds a code to the service.
     *
     * @param email The email address associated with the code.
     * @param codeType type of the code
     * @return Generated code
     */
    fun addCode(email: String, codeType: String): String

    /**
     * Checks if a code is valid.
     *
     * @param email The email address associated with the code.
     * @param code   The code to check.
     * @param codeType type of the code
     * @return True if the code is valid, false otherwise.
     */
    fun checkCode(email: String, code: String, codeType: CodeType): Boolean
}