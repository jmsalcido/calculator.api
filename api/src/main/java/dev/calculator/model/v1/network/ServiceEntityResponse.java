package dev.calculator.model.v1.network;

import dev.calculator.model.Service;
import dev.calculator.model.ServiceType;
import dev.calculator.model.Status;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ServiceEntityResponse {
    private long id;
    private UUID uuid;
    private ServiceType type;
    private double cost;
    private Status status;

    public static ServiceEntityResponse fromService(Service service) {
        return builder()
                .id(service.getId())
                .uuid(service.getUuid())
                .type(service.getType())
                .cost(service.getCost())
                .status(service.getStatus())
                .build();
    }
}
