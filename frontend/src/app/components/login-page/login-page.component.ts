import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css',
})
export class LoginPageComponent {
  loginError: boolean = false;
  constructor(private userService: UserService, private router: Router) {}

  onSubmit(username: string, password: string): void {
    this.userService.login(username, password).subscribe(
      (data) => {
        this.loginError = false;
        if (username == '00000000A') {
          this.router.navigate(['/profile_manager']);
        } else {
          this.router.navigate(['/profile']);
        }
      },
      (error) => {
        if (error.status === 401) {
          this.loginError = true;
        } else {
          this.router.navigate(['/error']);
        }
      }
    );
  }
}
