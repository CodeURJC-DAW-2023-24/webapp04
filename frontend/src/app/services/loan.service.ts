import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoanService {
  private api_url = "/api/loans";

  constructor(private http: HttpClient) {}

  getUserLoans(): Observable<any>{
    return this.http.get<any>(this.api_url);
  }

  calculateLoanPayments(amount: number, periods: number): Observable<any>{
    return this.http.post<any>(this.api_url, {amount, periods});
  }
}
