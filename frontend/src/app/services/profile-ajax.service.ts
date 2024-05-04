import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transfer } from '../models/transfer.model';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) { }

  getTransfers(accountId: string, startIndex: number, chunkSize: number): Observable<Transfer[]> {
    return this.http.get<Transfer[]>(`/api/accounts/${accountId}/transfers`, {
      params: {
        startIndex: startIndex.toString(),
        chunkSize: chunkSize.toString()
      }
    });
  }
}
