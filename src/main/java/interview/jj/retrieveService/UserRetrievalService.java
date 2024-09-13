package interview.jj.retrieveService;

import interview.jj.dao.TbUserRepository;
import interview.jj.entity.TbUserEntity;
import interview.jj.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRetrievalService {
    private final TbUserRepository tbUserRepository;

    public UserRetrievalService(TbUserRepository tbUserRepository) {
        this.tbUserRepository = tbUserRepository;
    }

    public boolean existsByEmail(String email) {
        return tbUserRepository.existsByEmail(email);
    }

    public TbUserEntity findByEmail(String email) {
        return tbUserRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }

}
