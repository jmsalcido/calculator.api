package dev.calculator.model.v1.network;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModifyRecordRequest {
    private long id;
    private String uuid;
    private long serviceId;
    private long userId;
    private double cost;
    private double userBalance;
    private String serviceResponse;
    private LocalDateTime date;
}
