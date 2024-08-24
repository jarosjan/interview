package interview.jj.service;

import interview.jj.dao.TbUserRepository;
import interview.jj.entity.TbUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class UserDetailService implements UserDetailsService {
    private final TbUserRepository tbUserRepository;

    @Autowired
    public UserDetailService(TbUserRepository tbUserRepository) {
        this.tbUserRepository = tbUserRepository;
    }

    // loadUserByUsername method is used by Spring Security to load user details
    // based on the username provided by the user, which is the email in this case
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", email);

        final TbUserEntity user = tbUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }

}