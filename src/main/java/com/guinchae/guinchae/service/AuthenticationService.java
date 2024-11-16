package com.guinchae.guinchae.service;

import com.guinchae.guinchae.email.EmailService;
import com.guinchae.guinchae.email.EmailTemplateName;
import com.guinchae.guinchae.model.UserModel;
import com.guinchae.guinchae.model.dto.AuthenticationRequestDto;
import com.guinchae.guinchae.model.dto.AuthenticationResponseDto;
import com.guinchae.guinchae.model.dto.RegistrationRequestDto;
import com.guinchae.guinchae.repository.RoleRepository;
import com.guinchae.guinchae.security.JwtService;
import com.guinchae.guinchae.model.TokenModel;
import com.guinchae.guinchae.repository.TokenRepository;
import com.guinchae.guinchae.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;


    public void register(RegistrationRequestDto registrationRequest) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("User role not found"));
        var user = UserModel.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(UserModel userModel) throws MessagingException {
        var newToken = generateAndSaveActivationToken(userModel);
        emailService.sendEmail(
                userModel.getEmail(),
                userModel.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Confirmação email Guincha-e"
        );
    }

    private String generateAndSaveActivationToken(UserModel userModel) {
        String generatedToken = generateActivationCode(8);
        var token = TokenModel.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .userModel(userModel)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((UserModel) authentication.getPrincipal());
        claims.put("email", user.getEmail());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken).build();
    }


    public void activateAccount(String token) throws MessagingException {
        TokenModel savedTokenModel = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalido!"));

        if(LocalDateTime.now().isAfter(savedTokenModel.getExpiresAt())) {
            sendValidationEmail(savedTokenModel.getUserModel());
            throw new RuntimeException("Token expirado! Um novo email foi enviado.");
        }
        var user = userRepository.findById(savedTokenModel.getUserModel().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        user.setEnabled(true);
        userRepository.save(user);
        savedTokenModel.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedTokenModel);
    }
}
