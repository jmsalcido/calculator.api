package dev.calculator.model.v1.network;

import dev.calculator.model.Service;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ModifyServiceResponse {
    private long id;
    private UUID uuid;
    private String type;
    private double cost;
    private String status;

    public static ModifyServiceResponse fromService(Service service) {
        return builder()
                .id(service.getId())
                .uuid(service.getUuid())
                .type(service.getType().name())
                .cost(service.getCost())
                .status(service.getStatus().name())
                .build();
    }
}
