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

  private register_url = "/api/accounts";

  constructor(private http: HttpClient) { }
  
  register(inputUser: string, name:string, surname:string, password: string, confirmPassword:string): Observable<any>{
    return this.http.post<any>(this.register_url, {inputUser, name, surname, password, confirmPassword});
  }

  loadMoreAccounts(page: number, size: number): Observable<Account[]> {
    const params = new HttpParams()
        .set('page', page.toString())
        .set('size', size.toString());
    return this.http.get<Account[]>(`/api/accounts`, { params });
  }

}
