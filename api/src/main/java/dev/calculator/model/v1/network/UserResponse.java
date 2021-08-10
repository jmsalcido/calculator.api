package dev.calculator.model.v1.network;

import dev.calculator.model.Status;
import dev.calculator.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private long id;
    private String username;
    private String uuid;
    private Status status;
    private String role;
    private Double userBalance;
    private LocalDateTime deletedAt;

    public static UserResponse fromUser(User user) {
        return builder()
                .id(user.getId())
                .uuid(user.getUuid().toString())
                .username(user.getUsername())
                .status(user.getStatus())
                .role(user.getRole().name())
                .userBalance(user.getUserBalance())
                .deletedAt(user.getDeletedAt())
                .build();
    }
}
