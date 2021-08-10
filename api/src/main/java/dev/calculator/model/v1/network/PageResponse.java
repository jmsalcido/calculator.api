package dev.calculator.model.v1.network;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResponse {
    int totalPages;
    int currentPage;
    int pageSize;
    List<Object> result;
}
