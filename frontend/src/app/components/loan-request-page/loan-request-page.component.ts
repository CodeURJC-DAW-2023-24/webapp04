import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-loan-request-page',
  templateUrl: './loan-request-page.component.html',
  styleUrl: './loan-request-page.component.css'
})
export class LoanRequestPageComponent {

  amount: number | null = null;
  installments: number | null = null;
  errorMessage: string = '';

  constructor(private router: Router){}

  // TODO Check that only authored users access loan request page

  onSubmit(): void {
    if (this.amount == null || this.installments == null) {
      this.errorMessage = 'Both amount and installments are required.';
    } else if (this.amount <= 0) {
      this.errorMessage = 'Amount must be greater than zero.';
    } else if (this.installments <= 0) {
      this.errorMessage = 'Installments must be greater than zero.';
    } else{
      this.router.navigate(['/loan_visualizer'], { queryParams: { amount: this.amount, periods: this.installments } })
    }
  }
}
