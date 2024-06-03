import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { RegisterPageComponent } from './components/register-page/register-page.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { Error401Component } from './components/error-401/error-401.component';
import { Error500Component } from './components/error-500/error-500.component';
import { Error404Component } from './components/error-404/error-404.component';
import { AccountListComponent } from './components/account-list/account-list.component';
import { TransfersManagerComponent } from './components/transfers-manager/transfers-manager.component';
import { TransferPageComponent } from './components/transfer-page/transfer-page.component';
import { LoanRequestPageComponent } from './components/loan-request-page/loan-request-page.component';
import { LoanVisualizerPageComponent } from './components/loan-visualizer-page/loan-visualizer-page.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterPageComponent },
  { path: 'profile', component: ProfilePageComponent },
  { path: 'error-404', component: Error404Component},
  { path: 'error-401', component: Error401Component},
  { path: 'error-500', component: Error500Component},
  { path: 'transfer', component: TransferPageComponent },
  { path: 'transfers', component: TransfersManagerComponent },
  { path: 'accounts', component: AccountListComponent },
  { path: 'loan_request', component: LoanRequestPageComponent},
  {path: 'loan_visualizer', component: LoanVisualizerPageComponent },
  { path: '**', redirectTo: 'home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
