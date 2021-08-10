package dev.calculator.model.v1.network;

import dev.calculator.model.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModifyRequest {
    private long id;
    private String username;
    private String password;
    private String uuid;
    private String role;
    private Status status;
    private double userBalance;
}
