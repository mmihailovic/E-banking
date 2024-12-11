package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.edu.raf.dto.ChangePasswordDTO;
import rs.edu.raf.dto.ChangePasswordWithCodeDTO;
import rs.edu.raf.dto.LoginDto;
import rs.edu.raf.exceptions.InvalidOldPasswordException;
import rs.edu.raf.exceptions.InvalidTokenException;
import rs.edu.raf.exceptions.UserNotFoundException;
import rs.edu.raf.model.code.CodeType;
import rs.edu.raf.model.user.User;
import rs.edu.raf.repository.UserRepository;
import rs.edu.raf.security.JwtUtil;
import rs.edu.raf.service.CodeService;
import rs.edu.raf.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CodeService codeService;
    private AuthenticationManager authenticationManager;

    @Override
    public String loginUser(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        return jwtUtil.generateToken(loginDto.username());
    }

    @Override
    public boolean changeUserPassword(ChangePasswordDTO changePasswordDTO) {
        User user = jwtUtil.getCurrentUser();
        if(bCryptPasswordEncoder.matches(changePasswordDTO.oldPassword(),user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(changePasswordDTO.newPassword()));
            userRepository.save(user);
            return true;
        }
        throw new InvalidOldPasswordException();
    }

    @Override
    public boolean changeUserPasswordWithCode(ChangePasswordWithCodeDTO changePasswordWithCodeDTO) {
        if(codeService.checkCode(changePasswordWithCodeDTO.email(), changePasswordWithCodeDTO.code(), CodeType.PASSWORD_RESET)) {
            User user = userRepository.findByEmailAndActiveIsTrue(changePasswordWithCodeDTO.email())
                    .orElseThrow(() -> new UserNotFoundException("User with email " + changePasswordWithCodeDTO.email() + " not found!"));
            user.setPassword(bCryptPasswordEncoder.encode(changePasswordWithCodeDTO.password()));
            userRepository.save(user);
            return true;
        }
        throw new InvalidTokenException("Token " + changePasswordWithCodeDTO.code() + " isn't valid!");
    }
}
