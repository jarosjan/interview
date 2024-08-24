package interview.jj.service;

import interview.jj.dao.TbUserRepository;
import interview.jj.entity.TbUserEntity;
import interview.jj.exception.NotFoundException;
import interview.jj.exception.UserAlreadyExistsException;
import interview.jj.model.ProjectResponse;
import interview.jj.model.UserEditRequest;
import interview.jj.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final TbUserRepository tbUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(TbUserRepository tbUserRepository, PasswordEncoder passwordEncoder) {
        this.tbUserRepository = tbUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String name, String password, String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (tbUserRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        TbUserEntity user = new TbUserEntity();
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        tbUserRepository.save(user);
    }

    @Transactional
    public void deleteUser(String email) {
        tbUserRepository.deleteByEmail(email);
    }

    public UserResponse getUser(String email) {
        final TbUserEntity entity = tbUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " does not exist"));

        return new UserResponse(
                entity.getName(),
                entity.getEmail(),
                entity.getProjects().stream().map(p -> new ProjectResponse(p.getId(), p.getName())).toList()
        );
    }

    public UserResponse updateUser(String email, UserEditRequest userRequest) {
        final TbUserEntity entity = tbUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " does not exist"));

        entity.setName(userRequest.name());
        tbUserRepository.save(entity);

        return new UserResponse(
                entity.getName(),
                entity.getEmail(),
                entity.getProjects().stream().map(p -> new ProjectResponse(p.getId(), p.getName())).toList()
        );
    }
}
