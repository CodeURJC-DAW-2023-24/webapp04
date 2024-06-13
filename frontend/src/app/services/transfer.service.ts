import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class TransferService {

  private api_url = "/api/transfer";

  constructor(private http: HttpClient) {}

  getTransfers(page: number, size: number): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get(this.api_url+"s", { params });
  }

  make_transfer(receiver_iban: string, amount: string): Observable<any>{
    return this.http.post(this.api_url, {receiver_iban, amount});
  }

}