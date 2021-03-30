import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

const API_AUTH = "http://localhost:8081/auth";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) {}

  public login(user:any): Observable<any>{
    return this.http.post(API_AUTH + "/login", {
      username: user.username,
      password: user.password
    });
  }

  public registration(user:any):Observable<any>{
    return this.http.post(API_AUTH + "/registration", {
      email: user.email,
      username: user.username,
      firstname:user.firstname,
      lastname: user.lastname,
      password: user.password,
      confirmPassword: user.confirmPassword
    });
  }
}
