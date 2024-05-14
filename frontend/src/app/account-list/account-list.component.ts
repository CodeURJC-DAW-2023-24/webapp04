import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { AccountsService, Account } from './services/accounts.service';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  accounts: Account[] = [];

  constructor(private accountsService: AccountsService) { }

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
}

  onLoadMoreClick(): void {
    const chunkSize = 10;
    const startIndex = Math.floor(this.accounts.length / 10) + 1;
    console.log(this.accounts);
    console.log(startIndex);
    this.loadMoreAccounts(startIndex, chunkSize);
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
