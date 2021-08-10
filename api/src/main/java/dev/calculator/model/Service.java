package dev.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "services")
@SQLDelete(sql = "UPDATE services SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at IS NULL")
@Data
@Builder
@AllArgsConstructor
public class Service {

    public Service() {
    }

    @Id
    @GeneratedValue(generator = "services_id_seq")
    private long id;
    private UUID uuid;
    @Enumerated(EnumType.STRING)
    private ServiceType type;
    private double cost;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime deletedAt;
}
