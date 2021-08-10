package dev.calculator.model.v1.network;

import lombok.Data;

@Data
public class ModifyServiceRequest {
    private long id;
    private String uuid;
    private String type;
    private double cost;
    private String status;
}
