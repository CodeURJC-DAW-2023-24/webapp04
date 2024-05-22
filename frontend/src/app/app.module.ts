import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

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
    TransfersManagerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
