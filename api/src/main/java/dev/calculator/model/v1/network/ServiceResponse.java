package dev.calculator.model.v1.network;

import dev.calculator.model.ServiceType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResponse {
    private final ServiceType serviceType;
    private final String resultString;
    private final double result;
}
