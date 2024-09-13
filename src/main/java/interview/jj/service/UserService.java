package interview.jj.service;

import interview.jj.dao.TbUserRepository;
import interview.jj.entity.TbUserEntity;
import interview.jj.exception.UserAlreadyExistsException;
import interview.jj.mapper.UserMapper;
import interview.jj.model.UserEditRequest;
import interview.jj.model.UserResponse;
import interview.jj.retrieveService.UserRetrievalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final TbUserRepository tbUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRetrievalService userRetrievalService;

    @Autowired
    public UserService(TbUserRepository tbUserRepository,
                       PasswordEncoder passwordEncoder,
                       UserRetrievalService userRetrievalService) {
        this.tbUserRepository = tbUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRetrievalService = userRetrievalService;
    }

    public void registerUser(String name, String password, String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (userRetrievalService.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }

        final TbUserEntity userEntity = UserMapper.toEntity(name, passwordEncoder.encode(password), email);
        tbUserRepository.save(userEntity);
    }

    @Transactional
    public void deleteUser(String email) {
        tbUserRepository.deleteByEmail(email);
    }

    public UserResponse getUser(String email) {
        return UserMapper.toResponse(userRetrievalService.findByEmail(email));
    }

    public UserResponse updateUser(String email, UserEditRequest userRequest) {
        final TbUserEntity entity = userRetrievalService.findByEmail(email);

        entity.setName(userRequest.name());
        tbUserRepository.save(entity);

        return UserMapper.toResponse(entity);
    }
}
