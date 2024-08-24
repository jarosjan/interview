package interview.jj.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "tb_project")
public class TbProjectEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private TbUserEntity user;

    public TbProjectEntity(UUID id, String name, TbUserEntity user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public TbProjectEntity() {

    }
}
