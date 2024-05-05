import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Transfer } from '../models/transfer.model';
import { DataService } from '../services/profile-ajax.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent {
  client_name: string;
  client_iban: string;
  client_balance: number;
  account_id: string;
  transfers: Transfer[] = [];


  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router, private transferService: DataService) {
    this.client_name = '';
    this.client_iban = '';
    this.account_id = '';
    this.client_balance = 0;
  }

  ngOnInit() {
    this.fetchProfileData();
  }

  fetchProfileData() {
    this.http.get('/api/accounts/personal', { observe: 'response' }).subscribe({
      next: (response) => {
        if (response.body) {
          const data = response.body as any;
          console.log(data);
          this.client_name = data.name + " " + data.surname;
          this.client_iban = data.iban;
          this.account_id = data.nip;
          this.client_balance = data.balance || 0;
          console.log("Everything is OK");
          this.loadMoreTransfers(0, 10);
        }
      },
      error: (error) => {
        console.error('Error al obtener datos del perfil:', error);
      }
    });
  }

  loadMoreTransfers(startIndex: number, chunkSize: number): void {
    this.transferService.getTransfers(this.account_id, startIndex, chunkSize)
      .subscribe((response: any) => {
        const content = response.content;
        if (content.length === 0) {
          const noTransfersElement = document.getElementById("no_transfers_performed");
          if (noTransfersElement) {
            noTransfersElement.style.display = "None";
          }
        }
        this.transfers.push(...content);
      });
  }

  onLoadMoreClick(): void {
    const chunkSize = 10;
    const startIndex = Math.floor(this.transfers.length / 10) + 1;
    console.log(this.transfers);
    console.log(startIndex);
    this.loadMoreTransfers(startIndex, chunkSize);
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
    })
  }
}
