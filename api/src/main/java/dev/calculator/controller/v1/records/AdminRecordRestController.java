package dev.calculator.controller.v1.records;

import dev.calculator.model.v1.network.ApiError;
import dev.calculator.model.v1.network.ModifyRecordRequest;
import dev.calculator.model.v1.network.PageResponse;
import dev.calculator.model.v1.network.RecordResponse;
import dev.calculator.service.v1.RecordService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/records/")
@Secured("ROLE_ADMIN")
public class AdminRecordRestController {

    private final RecordService recordService;

    public AdminRecordRestController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createRecord(@RequestBody ModifyRecordRequest request) {
        if (request.getId() > 0) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
                    "Id should not be present.",
                    "Id should not be present.");
            return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
        }

        RecordResponse response = recordService.modifyRecord(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateRecord(@RequestBody ModifyRecordRequest request) {
        RecordResponse response = recordService.modifyRecord(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{recordId}")
    public ResponseEntity<Long> deleteRecord(@PathVariable long recordId) {
        recordService.deleteRecord(recordId);
        return ResponseEntity.ok(recordId);
    }

    @GetMapping("/")
    public ResponseEntity<PageResponse> getAll(@RequestParam(required = false, defaultValue = "") String serviceType,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);

        PageResponse pageResponse = recordService.findByServiceTypeContaining(serviceType, pageRequest);
        return ResponseEntity.ok(pageResponse);
    }


}
