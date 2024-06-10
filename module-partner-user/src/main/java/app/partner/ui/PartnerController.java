package app.partner.ui;

import app.client.application.UserCommand;
import app.client.domain.ClientUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partner")
public class PartnerController {

    private final UserCommand userCommand;

    public PartnerController(
            UserCommand userCommand
    ) {
        this.userCommand = userCommand;
    }

    @GetMapping
    public String getPartner() {
        ClientUser user = userCommand.createUser("hello PartnerUser", "partner");
        return user.toString();
    }
}
