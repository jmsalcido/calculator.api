package dev.calculator.model.v1.network;

import dev.calculator.model.Record;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RecordResponse {
    private final long id;
    private final String uuid;
    private final ServiceEntityResponse service;
    private final UserResponse user;
    private final double cost;
    private final double userBalance;
    private final String serviceResponse;
    private final LocalDateTime date;

    public static RecordResponse fromRecord(Record record) {
        return builder()
                .id(record.getId())
                .uuid(record.getUuid().toString())
                .service(ServiceEntityResponse.fromService(record.getService()))
                .user(UserResponse.fromUser(record.getUser()))
                .cost(record.getCost())
                .userBalance(record.getUserBalance())
                .serviceResponse(record.getServiceResponse())
                .date(record.getDate())
                .build();
    }
}
