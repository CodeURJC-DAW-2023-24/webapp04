import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { AccountListComponent } from './components/account-list/account-list.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeadComponent } from './components/head/head.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { RegisterPageComponent } from './components/register-page/register-page.component';
import { ShortFooterComponent } from './components/short-footer/short-footer.component';
import { TopbarComponent } from './components/topbar/topbar.component';
import { TransferPageComponent } from './components/transfer-page/transfer-page.component';
import { TransfersManagerComponent } from './components/transfers-manager/transfers-manager.component';
import { HttpClientModule } from '@angular/common/http';
import { Error401Component } from './components/error-401/error-401.component';
import { Error500Component } from './components/error-500/error-500.component';
import { HeaderErrorComponent } from './components/header-error/header-error.component';
import { Error404Component } from './components/error-404/error-404.component';
import { LoanRequestPageComponent } from './components/loan-request-page/loan-request-page.component';
import { LoanVisualizerPageComponent } from './components/loan-visualizer-page/loan-visualizer-page.component';
import { ProfileManagerComponent } from './components/profile-manager/profile-manager.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AccountListComponent,
    FooterComponent,
    HeadComponent,
    HeaderComponent,
    LoginPageComponent,
    ProfilePageComponent,
    RegisterPageComponent,
    ShortFooterComponent,
    TopbarComponent,
    TransferPageComponent,
    TransfersManagerComponent,
    Error401Component,
    Error500Component,
    HeaderErrorComponent,
    Error404Component,
    LoanRequestPageComponent,
    LoanVisualizerPageComponent,
    ProfileManagerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
