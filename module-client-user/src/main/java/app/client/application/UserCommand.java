package app.client.application;

import app.client.domain.ClientUser;
import app.client.domain.ClientUserRepository;
import app.client.fegin.FundingFeginClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserCommand {

    private static final Logger log = LoggerFactory.getLogger(UserCommand.class);

    private final ClientUserRepository clientUserRepository;
    private final FundingFeginClient fundingFeginClient;

    public ClientUser createUser(String name, String email) {
        ClientUser clientUser = ClientUser.builder()
                .name(name)
                .email(email)
                .build();

        log.info("유저 생성: {}", clientUser);

        return clientUser;
    }

}
