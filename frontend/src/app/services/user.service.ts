import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private login_url = "/api/login";
  private logout_url = '/api/logout';
  private image_url = "/api/account/image";

  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.login_url, {username, password}).pipe(
      tap(response => {
        localStorage.setItem('user', JSON.stringify(response.user));
        localStorage.setItem('token', response.token);
      })
    );
  }

  logout(): Observable<any> {
    return this.http.post(this.logout_url, {}).pipe(
      tap(() => {
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        this.router.navigate(['/login']);
      })
    );
  }

  getClientImage(): Observable<Blob> {
    return this.http.get(this.image_url, { responseType: 'blob' });
  }

  uploadClientImage(imageFile: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('imageFile', imageFile, imageFile.name);
    return this.http.post(this.image_url, formData, { responseType: 'text' });
  }
}
