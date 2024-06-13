import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Transfer } from '../../models/transfer';
import { TransferService } from '../../services/transfer.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})

export class ProfilePageComponent implements OnInit {
  client_name: string;
  client_iban: string;
  client_balance: number;
  account_id: string;
  transfers: Transfer[] = [];
  currentPage = 0;
  pageSize = 10;
  client_image_url: string | undefined;

  constructor(
    private http: HttpClient, 
    private router: Router, 
    private transferService: TransferService, 
    private userService: UserService
  ) {
    this.client_name = '';
    this.client_iban = '';
    this.account_id = '';
    this.client_balance = 0;
  }

  ngOnInit() {
    this.fetchProfileData();
    this.fetchClientImage();
  }

  fetchProfileData() {
    this.http.get('/api/accounts/account', { observe: 'response' }).subscribe({
      next: (response) => {
        if (response.body) {
          const data = response.body as any;
          this.client_name = data.name + " " + data.surname;
          this.client_iban = data.iban;
          this.account_id = data.nip;
          this.client_balance = data.balance || 0;
          this.loadMoreTransfers();
        }
      },
      error: (error) => {
        if (error.status === 401) {
          this.router.navigate(['/error-401']);
        } else {
          console.error('Error: cannot obtain profile data', error);
          this.router.navigate(['/']);
        }
      }
    });
  }

  fetchClientImage() {
    this.userService.getClientImage().subscribe({
      next: (response) => {
        const objectURL = URL.createObjectURL(response);
        this.client_image_url = objectURL;
      },
      error: (error) => {
        console.error('Error: cannot get client`s image', error);
      }
    });
  }

  onImageSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      this.userService.uploadClientImage(file).subscribe(
        response => {
        console.log('Image uploaded successfully');
        this.fetchClientImage();
      }, error => {
        console.error('Error: cannot upload the new image', error);
      });
    }
  }

  loadMoreTransfers(): void {
    this.transferService.getTransfers(this.currentPage, this.pageSize)
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

  onLoadMoreClick(): void {
    this.loadMoreTransfers();
  }

  logout(): void {
    const body = {};
    this.http.post('/api/logout', body, { observe: 'response' }).subscribe({
      next: (response) => {
        console.log('Successful logout');
        this.router.navigate(['new/login']);
      },
      error: (error) => {
        console.error('Logout error', error);
      }
    });
  }
}
