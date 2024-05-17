import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Account {
  nip: string;
  iban: string;
  name: string;
  surname: string;
  imageBase64: string;
}

@Injectable({
  providedIn: 'root'
})
export class AccountsService {
  constructor(private http: HttpClient) { }

  loadMoreAccounts(startIndex: number, chunkSize: number): Observable<Account[]> {
    return this.http.get<Account[]>(`/account_load?startIndex=${startIndex}&chunkSize=${chunkSize}`);
  }
}
