import { Component, OnInit, Inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { TransfersManagerService, Transfer } from '../../services/transfers-manager.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-transfers-manager',
  templateUrl: './transfers-manager.component.html',
  styleUrls: ['./transfers-manager.component.css']
})
export class TransfersManagerComponent implements OnInit {
  transfers: Transfer[] = [];
  currentPage = 0;
  pageSize = 10;

  constructor(
    @Inject(TransfersManagerService) private transfersService: TransfersManagerService,
    private http: HttpClient,
    private router: Router,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadMoreTransfers();
  }

  loadMoreTransfers(): void {
      this.transfersService.loadMoreTransfers(this.currentPage, this.pageSize)
        .subscribe((response: any) => {
          const content = response.content || [];
          if (content.length === 0 && this.transfers.length === 0) {
            const noTransfersElement = document.getElementById("no_transfers_performed");
            if (noTransfersElement) {
              noTransfersElement.style.display = "block";
            }
          } else {
            this.transfers = [...this.transfers, ...content];
            this.currentPage++;
          }
        });
    }

    onLoadMoreTransfers(): void {
      this.loadMoreTransfers();
    }

  logout(): any {
    this.userService.logout().subscribe(
        response => console.log('Logout successful', response),
        error => console.error('Logout failed', error)
    );
  }
}
