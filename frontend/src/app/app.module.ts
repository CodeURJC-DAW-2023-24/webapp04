import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router'; // Importa RouterModule para las rutas

import { AppComponent } from './app.component';
import { HeadComponent } from './head/head.component';
import { HeaderComponent } from './header/header.component';
import { HomeComponent } from './home/home.component';
import { LoginPageComponent } from './login-page/login-page.component';
import { RegisterPageComponent } from './register-page/register-page.component';
import { ShortFooterComponent } from './short-footer/short-footer.component';
import { TopbarComponent } from './topbar/topbar.component';
import { FooterComponent } from './footer/footer.component';

const routes: Routes = [
  { path: 'new', component: HomeComponent },
  { path: 'new/login', component: LoginPageComponent },
  { path: 'new/register', component: RegisterPageComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    HeadComponent,
    HeaderComponent,
    HomeComponent,
    LoginPageComponent,
    RegisterPageComponent,
    ShortFooterComponent,
    TopbarComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
