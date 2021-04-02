import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {TokenService} from "../../service/token.service";
import {NotificationsService} from "../../service/notifications.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public registrationForm!: FormGroup;

  constructor(private authService: AuthService,
              private notificationService: NotificationsService,
              private router: Router,
              private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.registrationForm = this.createRegistrationForm();
  }

  createRegistrationForm():FormGroup {
    return this.formBuilder.group({
      email: ['', Validators.compose([Validators.required])],
      username: ['', Validators.compose([Validators.required])],
      firstname: ['', Validators.compose([Validators.required])],
      lastname: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(6)])],
      confirmPassword: ['', Validators.compose([Validators.required, Validators.minLength(6)])],
    })
  }
  submit(): void{
    this.authService.registration({
      email: this.registrationForm.value.email,
      username: this.registrationForm.value.username,
      firstname: this.registrationForm.value.firstname,
      lastname: this.registrationForm.value.lastname,
      password: this.registrationForm.value.password,
      confirmPassword: this.registrationForm.value.confirmPassword
    }).subscribe(data => {
      console.log(data);
      this.notificationService.showSnackBar("You are successfully registered!");
      //this.router.navigate(['login']);
    }, error => {
      console.log(error);
      this.notificationService.showSnackBar("Something went wrong!");
      //window.location.reload();
    });
  }
}
