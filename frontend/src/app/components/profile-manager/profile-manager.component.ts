import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-profile-manager',
  templateUrl: './profile-manager.component.html',
  styleUrl: './profile-manager.component.css',
})
export class ProfileManagerComponent {

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
