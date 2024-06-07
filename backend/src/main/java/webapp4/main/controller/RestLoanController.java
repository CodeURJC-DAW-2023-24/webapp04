package webapp4.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import webapp4.main.model.Loan;
import webapp4.main.model.Transfer;
import webapp4.main.repository.LoanRepository;
import webapp4.main.service.LoanService;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@CrossOrigin(origins = "*")
@RestController
public class RestLoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanService loanService;

    
    @Operation(summary = "Get paginated user loans")
    @ApiResponse(
            responseCode = "200",
            description = "Found the loans",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Loan.class))
    )
    @ApiResponse(responseCode = "404", description = "Loan Repository not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @GetMapping("/api/loans")
    public ResponseEntity<Page<Loan>> getAllUserLoans(Pageable page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        Page<Loan> userLoans = loanRepository.findByClientIdPaged(username, page);
        userLoans.forEach(loan -> {
            loan.getLoanPayments().size(); 
        });
        return ResponseEntity.ok(userLoans);  
    }

    @Operation (summary = "Calculate a loan for user")
    @ApiResponse(
            responseCode = "200",
            description = "transfer success",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Transfer.class))
    )
    @ApiResponse(responseCode = "404", description = "Loan Repository not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    @PostMapping("/api/loans")
    public ResponseEntity<?> createLoan(Model model, HttpServletRequest request, @RequestBody Loan loanJSON){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        if (loanJSON.getAmount() <= 0) {
            return ResponseEntity.badRequest().body("you can't create a loan with negative amount: " + loanJSON.getAmount());
        }

        if (loanJSON.getPeriods() <= 0) {
            return ResponseEntity.badRequest().body("you can't create a loan with zero/negative periods: " + loanJSON.getPeriods());
        }

        Object createLoan = loanService.addLoan(username, loanJSON.getAmount(), loanJSON.getPeriods());
        if(createLoan instanceof Loan){
            Loan loan = (Loan) createLoan;
            URI location = fromCurrentRequest().path("/{id}")
                    .buildAndExpand(loan.getLoan_id()).toUri();
            return ResponseEntity.created(location).body(loan);
        } else {
            String errorMessage = (String) createLoan;
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
}
