import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AccountService, Account } from '../../services/account.service';  // Correct import path

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {
  accounts: Account[] = [];

  constructor(
    @Inject(AccountService) private accountService: AccountService,
    private http: HttpClient,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadInitialAccounts();
  }

  loadInitialAccounts(): void {
    this.accountService.loadMoreAccounts(0, 10).subscribe((data: Account[]) => {
      this.accounts = data;
    });
  }

  onLoadMoreAccounts(): void {
    const startIndex: number = this.accounts.length;
    const chunkSize: number = 10;

    this.accountService.loadMoreAccounts(startIndex, chunkSize).subscribe((data: Account[]) => {
      this.accounts = this.accounts.concat(data);
    });
  }

  logout(): void {
    const body = {};
    this.http.post('/api/logout', body, { observe: 'response' }).subscribe({
      next: response => {
        console.log('Logout successful');
        this.router.navigate(['new/login']);
      },
      error: error => {
        console.error('Error during logout:', error);
      }
    });
  }
}
