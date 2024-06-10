package app.client.domain;

import java.util.List;
import java.util.Optional;

public interface ClientUserRepository {

    Optional<ClientUser> findById(Long id);

    List<ClientUser> findAll();

    ClientUser save(ClientUser clientUser);
}
