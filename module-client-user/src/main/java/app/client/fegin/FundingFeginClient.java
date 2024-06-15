package app.client.fegin;

import app.client.config.FeginConfig;
import app.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "funding",
        url = "${fegin.url.prefix}",
        configuration = FeginConfig.class
)
public interface FundingFeginClient {

    @GetMapping("/api/funding")
    ResponseEntity<ApiResponse> callGet(
            @RequestHeader("CustomHeader") String customHeader,
            @RequestParam("id") Long id
    );

}
