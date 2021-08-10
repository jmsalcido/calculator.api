package dev.calculator.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ServiceType {
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    SQUARE_ROOT,
    FREE_FORM,
    RANDOM_STRING;

    public static List<ServiceType> possibleFromName(String name) {
        return Arrays.stream(ServiceType.values())
                .filter(s -> s.name().contains(name.toUpperCase()))
                .collect(Collectors.toList());
    }

}
