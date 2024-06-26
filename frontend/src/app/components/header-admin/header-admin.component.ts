import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-header-admin',
  templateUrl: './header-admin.component.html',
  styleUrl: './header-admin.component.css'
})
export class HeaderAdminComponent {

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
