import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private login_url = "/api/login";

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string): Observable<any>{
    return this.http.post<any>(this.login_url, {username, password});
    const body = { username, password };
    this.http.post('/api/login', body, { observe: 'response' }).subscribe({
      next: (response) => {
        this.router.navigate(['/profile']);
      },
      error: (error) => {
        this.router.navigate(["/error"])
      }
    })
  }
}