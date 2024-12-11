package rs.edu.raf.service;

import rs.edu.raf.model.code.CodeType;

/**
 * This interface defines methods for managing codes.
 */
public interface CodeService {

    /**
     * Adds a code to the service.
     *
     * @param email The email address associated with the code.
     * @param codeType type of the code
     * @return Generated code
     */
    String addCode(String email, String codeType);

    /**
     * Checks if a code is valid.
     *
     * @param email The email address associated with the code.
     * @param code   The code to check.
     * @param codeType type of the code
     * @return True if the code is valid, false otherwise.
     */
    boolean checkCode(String email, String code, CodeType codeType);
}
