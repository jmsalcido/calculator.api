package dev.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@SQLDelete(sql = "UPDATE services SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at IS NULL")
@Builder
@AllArgsConstructor
public class Record {

    public Record() {
    }

    @Id
    @GeneratedValue(generator = "record_id_seq")
    private long id;
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "services_id")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
    private double cost;
    private double userBalance;
    private String serviceResponse;
    private LocalDateTime date;

    private LocalDateTime deletedAt;
}
