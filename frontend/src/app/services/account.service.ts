import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
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
export class AccountService {
  constructor(private http: HttpClient) { }

  loadMoreAccounts(page: number, size: number): Observable<Account[]> {
    const params = new HttpParams()
        .set('page', page.toString())
        .set('size', size.toString());
    return this.http.get<Account[]>(`/api/accounts`, { params });
  }

}
