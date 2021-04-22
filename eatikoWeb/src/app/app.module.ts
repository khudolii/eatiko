import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MaterialModule} from "./material.module";
import {HttpClientModule} from "@angular/common/http";
import {authInterceptorProviders} from "./helper/auth-interceptor.service";
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { NavigationComponent } from './layout/navigation/navigation.component';
import { IndexComponent } from "./layout/index/index.component";
import {FridgeComponent} from './layout/fridge/fridge.component';
import {MatRippleModule} from "@angular/material/core";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    NavigationComponent,
    IndexComponent,
    FridgeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MaterialModule,
    HttpClientModule,
    FormsModule,
    MatRippleModule,
    DragDropModule,
    FlexLayoutModule,
    MatPaginatorModule,
    MatTableModule
  ],
  providers: [authInterceptorProviders, /*authErrorInterceptorProvider*/],
  bootstrap: [AppComponent]
})
export class AppModule {
  public static API = "http://localhost:8081";
}
