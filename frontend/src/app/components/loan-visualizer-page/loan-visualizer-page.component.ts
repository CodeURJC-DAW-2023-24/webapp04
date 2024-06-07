import { Component, OnInit } from '@angular/core';
import { LoanService } from '../../services/loan.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-loan-visualizer-page',
  templateUrl: './loan-visualizer-page.component.html',
  styleUrl: './loan-visualizer-page.component.css'
})
export class LoanVisualizerPageComponent {

  amount: number = 0;
  periods: number = 0;
  loan: any = {};

  constructor(private loanService: LoanService, private route: ActivatedRoute) {}

  // TODO Check that only authored userloan: any = {};s access loan request page

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.amount = +params['amount'];
      this.periods = +params['periods'];

      this.loanService.calculateLoanPayments(this.amount, this.periods).subscribe(
        data => {
          console.log(data);
          this.loan = data;
        }, error => {
          console.error('Error al calcular el préstamo:', error);
        });
    });
  }
}
