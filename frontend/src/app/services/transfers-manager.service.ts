import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransfersManagerService {

  constructor(private http: HttpClient) { }

  loadMoreTransfers(startIndex: number, chunkSize: number): Observable<Account[]> {
    return this.http.get<Transfer[]>(`/api/transfers?startIndex=${startIndex}&chunkSize=${chunkSize}`);
  }
}

export interface Transfer {
    senderIBAN: string;
    receiverIBAN: string;
    date: string;
    transferType: string;
    amount: number;
}
