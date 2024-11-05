package interview.jj.dao;

import interview.jj.entity.TbProjectEntity;
import interview.jj.entity.TbUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TbProjectRepository extends JpaRepository<TbProjectEntity, Long> {

    Optional<TbProjectEntity> findByIdAndUser(UUID id, TbUserEntity user);

    List<TbProjectEntity> findByUser(TbUserEntity user);

    boolean existsByIdAndUser(UUID id, TbUserEntity user);

    void deleteByIdAndUser(UUID id, TbUserEntity user);

}
