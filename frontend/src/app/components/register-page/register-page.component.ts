import { Component } from '@angular/core';
import { AccountService } from '../../services/account.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.css'
})
export class RegisterPageComponent {
  constructor(private accountService: AccountService, private router: Router) {}

  onSubmit(inputUser: string, name: string, surname: string, password: string, confirmPassword: string): void {
    this.accountService.register(inputUser, name, surname, password, confirmPassword).subscribe(
      data => {
        this.router.navigate(['/login']);
      },
    );
  }

}
