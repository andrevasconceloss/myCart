package br.dev.vasconcelos.mycart.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "marketplace")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Marketplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "marketplace_name")
    private String name;

    @Column(name = "created_at", insertable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "marketplace_active")
    private boolean active;
}
