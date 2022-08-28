package tesis.droneimageprocessingapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import tesis.droneimageprocessingapi.config.user_details.UserDetailsImpl;
import tesis.droneimageprocessingapi.exception.NotFoundException;
import tesis.droneimageprocessingapi.model.User;
import tesis.droneimageprocessingapi.repository.UserRepository;

@Component
public class SessionUtils {

    private final UserRepository userRepository;

    @Autowired
    public SessionUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getLoggedUserInfo() {
        final UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundException("Cannot find logged user id"));
    }
}
