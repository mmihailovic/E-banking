package rs.edu.raf.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import rs.edu.raf.annotations.GeneratedCrudOperation;
import rs.edu.raf.dto.NotificationDTO;
import rs.edu.raf.exceptions.InvalidTokenException;
import rs.edu.raf.exceptions.UserNotFoundException;
import rs.edu.raf.model.code.Code;
import rs.edu.raf.model.code.CodeType;
import rs.edu.raf.model.user.User;
import rs.edu.raf.repository.CodeRepository;
import rs.edu.raf.repository.UserRepository;
import rs.edu.raf.service.CodeService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;

    private final UserRepository userRepository;
    private final JmsTemplate jmsTemplate;
    @Value("${notifications.destination}")
    private String destination;
    private final ObjectMapper objectMapper;

    @Override
    @GeneratedCrudOperation
    public String addCode(String email, String codeType) {
        try {
            User user = userRepository.findByEmailAndActiveIsTrue(email)
                    .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found!"));

            CodeType type = CodeType.valueOf(codeType);

            Optional<Code> existingCode = codeRepository.findByUserEmailAndCodeType(email, type);

            existingCode.ifPresent(codeRepository::delete);

            String code = UUID.randomUUID().toString();
            Long expirationDate = System.currentTimeMillis() + 1000 * 60 * 15;

            codeRepository.save(new Code(user, code, expirationDate, type));
            jmsTemplate.convertAndSend(destination,
                    objectMapper.writeValueAsString(new NotificationDTO(email, code, user.getLastName(), type.toString())));
            return code;
        } catch (JsonProcessingException exception) {
            exception.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public boolean checkCode(String email, String code, CodeType codeType) {
        Code codeObject = codeRepository.findByUserEmailAndCodeType(email, codeType)
                .orElseThrow(() -> new InvalidTokenException("Token " + code + " doesn't exist!"));
        
        if(codeObject.getCode().equals(code) && codeObject.getExpirationDate() >= System.currentTimeMillis()) {
            codeRepository.delete(codeObject);
            return true;
        }
        
        return false;
    }
}
