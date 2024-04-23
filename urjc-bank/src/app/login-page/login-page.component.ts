import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HeadComponent } from '../head/head.component';
import { ShortFooterComponent } from '../short-footer/short-footer.component';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [HeadComponent, ShortFooterComponent],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {

  constructor(private http: HttpClient) {}

  onSubmit(username: string, password: string): void {
    const body = { username: username, password: password };

    // Realiza la petición POST a la URL /api/login con los datos proporcionados
    this.http.post('/api/login', body).subscribe(response => {
      console.log('Respuesta del servidor:', response);
      // Aquí puedes manejar la respuesta del servidor
    }, error => {
      console.error('Error al hacer la petición:', error);
      // Aquí puedes manejar el error
    });
  }
}
