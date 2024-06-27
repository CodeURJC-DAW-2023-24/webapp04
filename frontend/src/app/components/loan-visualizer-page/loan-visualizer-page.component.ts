import { Component, OnInit } from '@angular/core';
import { LoanService } from '../../services/loan.service';
import { ActivatedRoute } from '@angular/router';
import { Chart } from 'chart.js/auto';
import { UserService } from '../../services/user.service';


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

  constructor(
    private loanService: LoanService,
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.amount = +params['amount'];
      this.periods = +params['periods'];

      this.loanService.calculateLoanPayments(this.amount, this.periods).subscribe(
        data => {
          this.loan = data;
          this.loanPayments = data.loanPayments;
          this.initChart();
          this.loadMorePayments();
        }, error => {
          console.error('Error al calcular el préstamo:', error);
        });
    });
  }

  initChart(): void {
    const canvas = document.getElementById('myChart') as HTMLCanvasElement;
    const ctx = canvas ? canvas.getContext('2d') : null;

    if (ctx) {
      this.myChart = new Chart(ctx, {
        type: 'bar',
        data: {
          labels: [],
          datasets: [
            {
              label: 'Amortised Capital',
              data: [],
              backgroundColor: 'rgba(255, 99, 132, 0.2)',
              borderColor: 'rgba(255, 99, 132, 1)',
              borderWidth: 1
            },
            {
              label: 'Interest',
              data: [],
              backgroundColor: 'rgba(54, 162, 235, 0.2)',
              borderColor: 'rgba(54, 162, 235, 1)',
              borderWidth: 1
            }
          ]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      });
    } else {
      console.error('Could not get canvas context.');
    }
  }

  updateChart(data: any[]): void {
    if (this.myChart) {
      const labels = Array.from({ length: data.length }, (_, i) => `Period ${i + 1}`);
      const capitalData = data.map(payment => payment.principal);
      const interestData = data.map(payment => payment.interest);

      this.myChart.data.labels = labels;
      this.myChart.data.datasets[0].data = capitalData;
      this.myChart.data.datasets[1].data = interestData;
      this.myChart.update();
    }
  }

  loadMorePayments(): void {
    const endIndex = this.startIndex + this.chunkSize;
    const newPayments = this.loanPayments.slice(this.startIndex, endIndex);
    this.displayedPayments = [...this.displayedPayments, ...newPayments];
    this.updateChart(this.displayedPayments);
    this.startIndex = endIndex;
  }

  onLoadMoreClick(): void {
    this.loadMorePayments();
  }

  logout(): any {
      this.userService.logout().subscribe(
          response => console.log('Logout successful', response),
          error => console.error('Logout failed', error)
      );
    }
}
