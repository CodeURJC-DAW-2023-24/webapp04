import { Component } from '@angular/core';
import { LoanService } from '../../services/loan.service';

@Component({
  selector: 'app-loan-request-page',
  templateUrl: './loan-request-page.component.html',
  styleUrl: './loan-request-page.component.css'
})
export class LoanRequestPageComponent {

  constructor(private loanService: LoanService){}

  onSubmit(amount: string, installments: string): void {
    this.loanService.calculateLoanPayments(amount, installments);
  }
}
