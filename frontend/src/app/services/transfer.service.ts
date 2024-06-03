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
    console.log(body);
    this.http.post('/api/accounts/transfer', body, { observe: 'response' }).subscribe({
      next: (response) => {
        console.log(response);
        console.log("Transacción realizada");
      },
      error: (error) => {
        console.log("Error occurred while making transfer");
        console.log(error);
      }
    });
    this.router.navigate(['profile'])
  }

}