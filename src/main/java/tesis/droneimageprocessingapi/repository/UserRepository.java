package tesis.droneimageprocessingapi.repository;

import org.springframework.data.repository.CrudRepository;
import tesis.droneimageprocessingapi.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findUserByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
