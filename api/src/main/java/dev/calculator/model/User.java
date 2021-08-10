package dev.calculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at IS NULL")
@Data
@Builder
@AllArgsConstructor
public class User {

    public User() {
    }

    @Id
    @GeneratedValue(generator = "users_id_seq")
    private long id;
    private UUID uuid;
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;

    private Double userBalance;

    private LocalDateTime deletedAt;
}
