import {Component, OnInit} from '@angular/core';
import {LoginService} from '../services/login.service';
import {Router} from '@angular/router';
import {User} from '../model/user';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  user: User;

  constructor(private loginService: LoginService, private router: Router) {
  }

  ngOnInit(): void {
  }

  login() {
    let token: string;
    this.loginService.login(this.user).subscribe(data => token = data, error => {
    }, () => this.completeLogin(token));
  }

  completeLogin(token: string) {
    localStorage.setItem('user', token);
  }
}
