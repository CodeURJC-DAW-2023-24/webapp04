import { Component } from '@angular/core';
import { HeadComponent } from '../head/head.component';
import { TopbarComponent } from '../topbar/topbar.component';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [HeadComponent, TopbarComponent, HeaderComponent, FooterComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
