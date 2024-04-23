package webapp4.main.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapp4.main.security.jwt.AuthResponse;
import webapp4.main.security.jwt.LoginRequest;
import webapp4.main.security.jwt.UserLoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RestLoginController {
    @Autowired
    private UserLoginService userService;

    @Operation(summary = "User login through form")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User logged in successfully",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<AuthResponse> loginForm(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @ModelAttribute LoginRequest loginRequest) {
        return userService.login(loginRequest, accessToken, refreshToken);
    }

    @Operation(summary = "User login through form")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User logged in successfully",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> loginJson(
            @CookieValue(name = "accessToken", required = false) String accessToken,
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest, accessToken, refreshToken);
    }

    @Operation(summary = "Checks if the user logged in is the same as the user in the token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User is logged in",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content(mediaType = "application/json")
            )
    })

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {
        return userService.refresh(refreshToken);
    }

    @Operation(summary = "Logs out the user")
    @ApiResponse(
            responseCode = "200",
            description = "User is logged out",
            content = @Content(mediaType = "application/json")
    )
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logOut(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(new AuthResponse(AuthResponse.Status.SUCCESS, userService.logout(request, response)));
    }

}