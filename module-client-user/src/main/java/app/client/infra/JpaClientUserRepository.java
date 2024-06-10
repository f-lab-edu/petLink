package app.client.infra;

import app.client.domain.ClientUser;
import app.client.domain.ClientUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClientUserRepository extends ClientUserRepository, JpaRepository<ClientUser, Long> {
}
