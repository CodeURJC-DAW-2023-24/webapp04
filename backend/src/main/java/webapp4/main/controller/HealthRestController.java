package webapp4.main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthRestController {
    @GetMapping("/health")
    @Operation(summary = "Retrieves the categories available as well as the page and number of pages")
    @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    public ResponseEntity<?> health(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/priv_health")
    @Operation(summary = "Retrieves the categories available as well as the page and number of pages")
    @ApiResponse(responseCode = "404", description = "Account not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    public ResponseEntity<?> privHealth(){
        return ResponseEntity.ok().build();
    }
}

