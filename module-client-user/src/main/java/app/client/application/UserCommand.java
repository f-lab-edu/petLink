package app.client.application;

import app.client.domain.ClientUser;
import app.client.infra.JpaClientUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserCommand {

    private static final Logger log = LoggerFactory.getLogger(UserCommand.class);

    private final JpaClientUserRepository clientUserRepository;

    public UserCommand(JpaClientUserRepository clientUserRepository) {
        this.clientUserRepository = clientUserRepository;
    }

    public ClientUser createUser(String name, String email) {
        ClientUser clientUser = ClientUser.builder()
                .name(name)
                .email(email)
                .build();

        log.info("유저 생성: {}", clientUser);

        return clientUser;
    }
}
