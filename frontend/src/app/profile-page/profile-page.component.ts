import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent {
  client_name: string;
  client_iban: string;
  client_balance: number;
  username: string;


  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {
    this.client_name = '';
    this.client_iban = '';
    this.client_balance = 0;
    this.username = '';
  }

  ngOnInit() {
    const username = this.route.snapshot.queryParams['username'];
    this.username = username;
    console.log(username);
    this.fetchProfileData();
  }

  fetchProfileData() {
    this.http.get(`/api/accounts/${this.username}`, { observe: 'response' }).subscribe({
      next: (response) => {
        if (response.body) {
          const data = response.body as any; // Utilizamos any para manejar cualquier estructura de datos
        this.client_name = data.name;
        this.client_iban = data.iban;
        this.client_balance = data.balance || 0; // Si la propiedad balance no está presente, establecemos un valor predeterminado
        console.log("Everything is OK");
        }
      },
      error: (error) => {
        console.error('Error al obtener datos del perfil:', error);
      }
    });
  }

}
