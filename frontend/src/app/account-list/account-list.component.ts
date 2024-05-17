import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AccountsService, Account } from '../services/accounts.service';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {
  accounts: Account[] = [];

  constructor(
    private accountsService: AccountsService,
    private http: HttpClient,
    private router: Router) { }

  ngOnInit(): void {
    this.loadInitialAccounts();
  }

  loadInitialAccounts(): void {
    this.accountsService.loadMoreAccounts(0, 10).subscribe(data => {
      this.accounts = data;
    });
  }

  onLoadMoreAccounts(): void {
    const startIndex: number = this.accounts.length;
    const chunkSize: number = 10;

    this.accountsService.loadMoreAccounts(startIndex, chunkSize).subscribe(data => {
      this.accounts = this.accounts.concat(data);
    });
  }
  logout(): void {
    const body = {};
    this.http.post('/api/logout', body, { observe: 'response' }).subscribe({
      next: response => {
        console.log('Logout exitoso');
        this.router.navigate(['new/login']);
      },
      error: error => {
        console.error('Error en logout:', error);
      }
    });
  }
}
