import { Component, OnInit } from '@angular/core';
import { LoanService } from '../../services/loan.service';
import { ActivatedRoute } from '@angular/router';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-loan-visualizer-page',
  templateUrl: './loan-visualizer-page.component.html',
  styleUrls: ['./loan-visualizer-page.component.css']
})
export class LoanVisualizerPageComponent implements OnInit {

  amount: number = 0;
  periods: number = 0;
  loan: any = {};
  loanPayments: any[] = [];
  displayedPayments: any[] = [];
  startIndex: number = 0;
  chunkSize: number = 10;
  myChart: Chart | undefined;

  constructor(private loanService: LoanService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.amount = +params['amount'];
      this.periods = +params['periods'];

      this.loanService.calculateLoanPayments(this.amount, this.periods).subscribe(
        data => {
          this.loan = data;
          this.loanPayments = data.loanPayments;
          this.loadMorePayments();
        }, error => {
          console.error('Error al calcular el préstamo:', error);
        });
    });
  }

  loadMorePayments(): void {
    const endIndex = this.startIndex + this.chunkSize;
    const newPayments = this.loanPayments.slice(this.startIndex, endIndex);
    this.displayedPayments = [...this.displayedPayments, ...newPayments];
    this.startIndex = endIndex;
  }

  onLoadMoreClick(): void {
    this.loadMorePayments();
  }
}
