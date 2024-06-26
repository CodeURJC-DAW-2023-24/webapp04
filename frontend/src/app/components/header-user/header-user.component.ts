import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-header-user',
  templateUrl: './header-user.component.html',
  styleUrl: './header-user.component.css'
})
export class HeaderUserComponent {

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
