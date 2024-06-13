import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class TransferService {

  private api_url = "/api/transfer";

  constructor(private http: HttpClient) {}

  make_transfer(receiver_iban: string, amount: string): Observable<any>{
    return this.http.post(this.api_url, {receiver_iban, amount});
  }

}