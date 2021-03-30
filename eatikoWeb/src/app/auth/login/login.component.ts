import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {TokenService} from "../../service/token.service";
import {Router} from "@angular/router";
import {NotificationsService} from "../../service/notifications.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loginForm!: FormGroup;

  constructor(
    private authService: AuthService,
    private tokenService: TokenService,
    private notificationService: NotificationsService,
    private router: Router,
    private formBuilder: FormBuilder) {
    if(this.tokenService.getUser()){
      this.router.navigate(['/']);
    }
  }


  ngOnInit(): void {
    this.loginForm = this.createLoginForm();
  }

  createLoginForm():FormGroup {
    return this.formBuilder.group({
        username: ['', Validators.compose([Validators.required])],
        password: ['', Validators.compose([Validators.required])]
      });
  }

  submit():void {
    this.authService.login({
      username: this.loginForm.value.username,
      password: this.loginForm.value.password
    }).subscribe(data => {
      console.log(data);
      this.tokenService.saveToken(data.token);
      this.tokenService.saveUser(data);
      this.notificationService.showSnackBar("You are successfully logged in!");
      this.router.navigate(['/']);
      window.location.reload();
    }, error => {
      console.log(error);
      this.notificationService.showSnackBar(error.message);
      window.location.reload();
    })
  }
}
