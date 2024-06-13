import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Transfer } from '../../models/transfer';
import { ProfileAjaxService } from '../../services/profile-ajax.service';
import { UserService } from '../../services/user.service';

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
  client_image_url: string | undefined;


  constructor(private http: HttpClient, private router: Router, private transferService: ProfileAjaxService, private userService: UserService) {
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
        if (error.status === 401){
          this.router.navigate(['/error-401']);
        } else{
          console.error('Error al obtener datos del perfil:', error);
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
      this.userService.uploadClientImage(file).subscribe(response => {
        console.log('Image uploaded sucessfully');
        this.fetchClientImage();
      }, error => {
        console.error('Error: cannot upload the new image', error);
      });
    }
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
        console.log('Successfull logout');
        this.router.navigate(['new/login']);
      },
      error: (error) => {
        console.error('Logout error', error);
      }
    })
  }
}
