import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AccountService, Account } from '../../services/account.service';  // Correct import path
import { UserService } from '../../services/user.service';

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
    private router: Router,
    private userService: UserService
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

  logout(): any {
    this.userService.logout().subscribe(
        response => console.log('Logout successful', response),
        error => console.error('Logout failed', error)
    );
  }
}
