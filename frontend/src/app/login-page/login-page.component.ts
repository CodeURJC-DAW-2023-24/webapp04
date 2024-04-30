import { Component } from '@angular/core';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {

  constructor(private userSerice: UserService) {}

  onSubmit(username: string, password: string): void {
    this.userSerice.login(username, password)
  }
}