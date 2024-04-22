import { Component } from '@angular/core';
import { HeadComponent } from '../head/head.component'
import { HeaderComponent } from '../header/header.component'
import { ShortFooterComponent } from '../short-footer/short-footer.component'

@Component({
  selector: 'app-register-page',
  standalone: true,
  imports: [HeadComponent, HeaderComponent, ShortFooterComponent],
  templateUrl: './register-page.component.html',
  styleUrl: './register-page.component.css'
})
export class RegisterPageComponent {

}
