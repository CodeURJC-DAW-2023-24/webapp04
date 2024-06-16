import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransfersManagerService {

  constructor(private http: HttpClient) { }

  loadMoreTransfers(page: number, size: number): Observable<Transfer[]> {
    const params = new HttpParams()
        .set('page', page.toString())
        .set('size', size.toString());
    return this.http.get<Transfer[]>(`/api/transferslist`, { params });
  }
}

export interface Transfer {
    senderIBAN: string;
    receiverIBAN: string;
    date: string;
    transferType: string;
    amount: number;
}
