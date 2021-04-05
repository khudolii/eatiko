import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MaterialModule} from "./material.module";
import {HttpClientModule} from "@angular/common/http";
import {authInterceptorProviders} from "./helper/auth-interceptor.service";
import {authErrorInterceptorProvider} from "./helper/error-interceptor.service";
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { NavigationComponent } from './layout/navigation/navigation.component';
import { IndexComponent } from "./layout/index/index.component";
import { FridgeComponent } from './layout/fridge/fridge.component';
import {MatRippleModule} from "@angular/material/core";
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NavigationComponent,
    IndexComponent,
    FridgeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    MatRippleModule
  ],
  providers: [authInterceptorProviders, /*authErrorInterceptorProvider*/],
  bootstrap: [AppComponent]
})
export class AppModule {
  public static API = "http://localhost:8081";
}
