package dev.calculator.model.v1.network;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServiceRequest {
    String serviceType;
    List<Integer> numbers = new ArrayList<>();
    Double number;
    String freeForm;
}
