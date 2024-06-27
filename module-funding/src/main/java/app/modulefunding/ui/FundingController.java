package app.modulefunding.ui;

import app.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/funding")
public class FundingController {

    @GetMapping
    public ResponseEntity<?> getFunding(
            @RequestHeader(value = "CustomHeader", required = false) String customHeader,
            @RequestParam(value = "id", required = false, defaultValue = "999") Long id
    ) {
        log.info("funding 모듈 호출됨");
        ApiResponse apiResponse = new ApiResponse(
                200,
                customHeader,
                "id : " + id
        );

        return ResponseEntity.ok(apiResponse);
    }
}
