package rs.edu.raf.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rs.edu.raf.dto.*;
import rs.edu.raf.exceptions.InvalidOldPasswordException;
import rs.edu.raf.exceptions.InvalidTokenException;
import rs.edu.raf.exceptions.UserNotFoundException;
import rs.edu.raf.mapper.UserMapper;
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
    private UserMapper userMapper;

    @Override
    public LoginResponseDTO loginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password()));
        String token = jwtUtil.generateToken(loginDto.username());
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return new LoginResponseDTO(userMapper.userToLoggedUserDTO((User)user), token);
    }

    @Override
    public LoggedUserDTO getLoggedUser() {
        return userMapper.userToLoggedUserDTO((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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
