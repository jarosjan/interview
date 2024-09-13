package interview.jj.retrieveService;

import interview.jj.dao.TbProjectRepository;
import interview.jj.entity.TbProjectEntity;
import interview.jj.entity.TbUserEntity;
import interview.jj.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectRetrievalService {
    private final TbProjectRepository tbProjectRepository;

    public ProjectRetrievalService(TbProjectRepository tbProjectRepository) {
        this.tbProjectRepository = tbProjectRepository;
    }

    public TbProjectEntity findByIdAndUser(UUID uuid, TbUserEntity userEntity) {
        return tbProjectRepository.findByIdAndUser(uuid, userEntity)
                .orElseThrow(() -> new NotFoundException("Project with projectId " + uuid + " does not exist"));
    }

    public boolean existsByIdAndUser(UUID uuid, TbUserEntity userEntity) {
        return tbProjectRepository.existsByIdAndUser(uuid, userEntity);
    }

}
