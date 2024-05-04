import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string){
    const body = { username, password };

    this.http.post('/api/login', body, { observe: 'response' }).subscribe({
      next: (response) => {
        console.log(username);
        console.log(response);
        this.router.navigate(['new/profile'], { queryParams: { username: username }, queryParamsHandling: 'merge' });
      },
      error: (error) => {
        this.router.navigate(["/error"])
      }
    })
    };

}
