import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { TransfersManagerService, Transfer } from '../services/transfers-manager.service';

@Component({
  selector: 'app-transfers-manager',
  templateUrl: './transfers-manager.component.html',
  styleUrls: ['./transfers-manager.component.css']
})
export class TransfersManagerComponent implements OnInit {
  transfers: Transfer[] = [];

  constructor(
    private transfersService: TransfersManagerService,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadInitialTransfers();
  }

  loadInitialTransfers(): void {
    this.transfersService.loadMoreTransfers(0, 10).subscribe((data: Transfer[]) => {
      this.transfers = data;
    });
  }

  onLoadMoreTransfers(): void {
    const startIndex: number = this.transfers.length;
    const chunkSize: number = 10;

    this.transfersService.loadMoreTransfers(startIndex, chunkSize).subscribe((data: Transfer[]) => {
      this.transfers = this.transfers.concat(data);
    });
  }

  logout(): void {
    const body = {};
    this.http.post('/api/logout', body, { observe: 'response' }).subscribe({
      next: (response) => {
        console.log('Logout exitoso');
        this.router.navigate(['new/login']);
      },
      error: (error) => {
        console.error('Error en logout:', error);
      }
    });
  }
}
