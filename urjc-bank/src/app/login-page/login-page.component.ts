import { Component } from '@angular/core';
import { HeadComponent } from '../head/head.component';
import { ShortFooterComponent } from '../short-footer/short-footer.component';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [HeadComponent, ShortFooterComponent],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {

}
