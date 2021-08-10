package dev.calculator.controller.v1.records;

import dev.calculator.model.v1.network.PageResponse;
import dev.calculator.service.v1.RecordService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/records/")
public class RecordRestController {

    private final RecordService recordService;

    public RecordRestController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/user")
    public ResponseEntity<PageResponse> getRecordsForUser(Authentication authentication,
                                               @RequestParam(required = false) String serviceType,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        PageResponse pageResponse = recordService.findForUser(authentication.getName(), serviceType, pageRequest);
        return ResponseEntity.ok(pageResponse);
    }


}
