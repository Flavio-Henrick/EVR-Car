package br.com.evrcar.service;

import br.com.evrcar.dto.*;
import br.com.evrcar.repository.AdminRepository;
import br.com.evrcar.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class AuthService {
    private final AdminRepository admins;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final String bootstrapUser, bootstrapPassword;

    public AuthService(AdminRepository admins, PasswordEncoder encoder, JwtService jwt,
      @Value("${app.admin.username:}") String bootstrapUser,
      @Value("${app.admin.password:}") String bootstrapPassword) {
        this.admins=admins; this.encoder=encoder; this.jwt=jwt;
        this.bootstrapUser=bootstrapUser; this.bootstrapPassword=bootstrapPassword;
    }

    public LoginResponse login(LoginRequest request) {
        var admin = admins.findByUsername(request.username()).orElse(null);
        boolean valid = admin != null
            ? encoder.matches(request.password(), admin.getPasswordHash())
            : request.username().equals(bootstrapUser) && !bootstrapPassword.isBlank()
                && request.password().equals(bootstrapPassword);
        if (!valid) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
        return new LoginResponse(jwt.generate(request.username()), "Bearer", 43200);
    }
}
