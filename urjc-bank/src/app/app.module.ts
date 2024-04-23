import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'; 

import { AppComponent } from './app.component';
import { LoginPageComponent } from './login-page/login-page.component';

@NgModule({
  imports: [
    FormsModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    LoginPageComponent
  ],
  providers: [],
  bootstrap: [ AppComponent ]
})
export class AppModule { }