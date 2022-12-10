package tesis.droneimageprocessingapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tesis.droneimageprocessingapi.config.JwtUtils;
import tesis.droneimageprocessingapi.config.user_details.UserDetailsImpl;
import tesis.droneimageprocessingapi.dto.LoginRequest;
import tesis.droneimageprocessingapi.dto.LoginResponse;
import tesis.droneimageprocessingapi.dto.RegisterRequest;
import tesis.droneimageprocessingapi.exception.BadRequestException;
import tesis.droneimageprocessingapi.exception.NotFoundException;
import tesis.droneimageprocessingapi.model.User;
import tesis.droneimageprocessingapi.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        return LoginResponse.from(user, jwt);
    }

    public User registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername()))
            throw new BadRequestException("El usuario ya se encuentra en uso");

        if (userRepository.existsByEmail(registerRequest.getEmail()))
            throw new BadRequestException("El email ya se encuentra en uso");

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        return userRepository.save(user);
    }

}
