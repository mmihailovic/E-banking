package rs.edu.raf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.edu.raf.model.user.User;
import rs.edu.raf.repository.UserRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailAndActiveIsTrue(username);

        if(user.isPresent())
            return new org.springframework.security.core.userdetails.User(
                    user.get().getEmail(), user.get().getPassword(), user.get().getAuthorities());

        throw new UsernameNotFoundException(username);
    }
}
