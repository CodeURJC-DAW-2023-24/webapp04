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


  constructor(private http: HttpClient, private route: ActivatedRoute, private router: Router) {
    this.client_name = '';
    this.client_iban = '';
    this.client_balance = 0;
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      const username = params['id'];
      this.fetchProfileData(username);
    });
  }

  fetchProfileData(username: string) {
    this.http.get(`/api/accounts/${username}`, { observe: 'response' }).subscribe({
      next: (response) => {
        if (response.body) {
          const data = response.body;
          this.client_name = data.name;
          this.client_iban = data.iban;
          this.client_balance = data.balance;
        }
      },
      error: (error) => {
        console.error('Error al obtener datos del perfil:', error);
      }
    });
  }

}
