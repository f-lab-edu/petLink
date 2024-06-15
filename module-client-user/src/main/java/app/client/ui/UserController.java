package app.client.ui;

import app.client.application.UserCommand;
import app.client.domain.ClientUser;
import app.client.fegin.FundingFeginClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserCommand userCommand;
    private final FundingFeginClient fundingFeginClient;

    @GetMapping
    public ResponseEntity<?> getUser() {
        return fundingFeginClient.callGet(
                "this is custom header",
                2L
        );
    }

    @GetMapping("/create")
    public ResponseEntity<?> createUser(
            String name,
            String email
    ) {
        ClientUser user = userCommand.createUser(name, email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @RequestBody Object signUpRequest
    ) {
        return ResponseEntity.ok("signUp");
    }

    @GetMapping("/duplicate/{name}")
    public ResponseEntity<?> verifyNameExistence(
            @PathVariable String name
    ) {
        return ResponseEntity.ok("verifyNameExistence");
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(@CookieValue("token") String token) {
        return ResponseEntity.ok("withdrawal");
    }

}
