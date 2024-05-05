import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TransferService {

  constructor(private http: HttpClient, private router: Router) {}

  make_transfer(receiver_iban: string, amount: string){
    const body = { receiver_iban, amount };

    this.http.post('/api/accounts/make_transfer', body, { observe: 'response' }).subscribe({
      next: (response) => {
        console.log(response);
        this.router.navigate(['new/profile']);
      },
      error: (error) => {
        this.router.navigate(["/error"])
      }
    })
    }
}
