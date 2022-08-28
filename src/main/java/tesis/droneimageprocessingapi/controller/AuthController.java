package tesis.droneimageprocessingapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tesis.droneimageprocessingapi.dto.LoginRequest;
import tesis.droneimageprocessingapi.dto.LoginResponse;
import tesis.droneimageprocessingapi.dto.RegisterRequest;
import tesis.droneimageprocessingapi.model.User;
import tesis.droneimageprocessingapi.service.AuthService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

}
