package app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse(
        Integer code,
        String message,
        Object data
) {
    @Builder
    public ApiResponse {
    }
}
