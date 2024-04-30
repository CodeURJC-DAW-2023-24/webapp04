import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {}


  login(username: string, password: string){
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
