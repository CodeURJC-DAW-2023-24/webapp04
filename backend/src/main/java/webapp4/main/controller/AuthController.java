package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapp4.main.security.jwt.AuthResponse;
import webapp4.main.security.jwt.LoginRequest;
import webapp4.main.security.jwt.UserLoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest,
                                              @CookieValue(name = "AccessToken", required = false) String accessToken,
                                              @CookieValue(name = "RefreshToken", required = false) String refreshToken) {
        return userLoginService.login(loginRequest, accessToken, refreshToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@CookieValue(name = "RefreshToken") String refreshToken) {
        return userLoginService.refresh(refreshToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        String logoutMessage = userLoginService.logout(request, response);
        return ResponseEntity.ok(logoutMessage);
    }
}
