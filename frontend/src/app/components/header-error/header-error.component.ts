import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';


@Component({
  selector: 'app-header-error',
  templateUrl: './header-error.component.html',
  styleUrl: './header-error.component.css'
})
export class HeaderErrorComponent {

  constructor(
    private userService: UserService
  ) {}

  logout(): any {
    this.userService.logout().subscribe(
        response => console.log('Logout successful', response),
        error => console.error('Logout failed', error)
    );
  }
}
