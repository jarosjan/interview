package interview.jj.dao;

import interview.jj.entity.TbUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TbUserRepository extends JpaRepository<TbUserEntity, Long> {
    Optional<TbUserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
