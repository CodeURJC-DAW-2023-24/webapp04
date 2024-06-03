import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegisterPageComponent } from './components/register-page/register-page.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { Error401Component } from './components/error-401/error-401.component';
import { Error500Component } from './components/error-500/error-500.component';
import { Error404Component } from './components/error-404/error-404.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterPageComponent },
  { path: 'profile/:id', component: ProfilePageComponent },
  { path: 'error-404', component: Error404Component},
  { path: 'error-401', component: Error401Component},
  { path: 'error-500', component: Error500Component},
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
