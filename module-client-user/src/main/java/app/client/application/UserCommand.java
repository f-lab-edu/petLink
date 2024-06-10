package app.client.application;

import app.client.domain.ClientUser;
import org.springframework.stereotype.Service;

@Service
public class UserCommand {

    public ClientUser createUser(String name, String email) {
        ClientUser clientUser = new ClientUser(name, email);
        System.out.println("UserCommand.createUser: " + clientUser);
        return clientUser;
    }
}
