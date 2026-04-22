import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/security/auth.service';
import {Router} from '@angular/router';
import {tokenName} from '@angular/compiler';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public usernameError: string;
  public passwordError: string;
  public showPassword = false;

  constructor(private authService: AuthService, private router: Router) { }

  isUserNameValid = false;
  isPasswordValid = false;
  message = '';

  ngOnInit(): void {
  }
// tslint:disable-next-line:typedef
  clearUserName() {
    this.isUserNameValid = false;
    this.usernameError = '';
    this.message = '';
  }

  // tslint:disable-next-line:typedef
  clearPassword() {
    this.isPasswordValid = false;
    this.passwordError = '';
    this.message = '';
  }

  // tslint:disable-next-line:typedef
  login(username: string, password: string) {
    if (!this.validateInputs(username, password)) {
      return;
    }

    this.authService.login(username, password).subscribe({
      next: response => {
        sessionStorage.setItem('token', response.token);
        sessionStorage.setItem('username', response.username);
        sessionStorage.setItem('userPassword', response.password);
        sessionStorage.setItem('roles', response.roles);
        this.router.navigateByUrl('/products');
      },
      error: errorResponse => {
        // Clear previous error messages
        this.usernameError = '';
        this.passwordError = '';
        this.message = '';

        const bundle = errorResponse.error?.bundleMessage;

        if (Array.isArray(bundle)) {
          this.message = bundle
            .filter(e => e.field === 'system')
            .map(e => e.message)
            .join('\n');
        } else {
          this.message = 'An unexpected error occurred.';
        }
      }
    });
  }


  // tslint:disable-next-line:typedef
  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  // tslint:disable-next-line:typedef
  private validateInputs(userName: string, password: string) {
    if (!userName) {
      this.isUserNameValid = true;
      return false;
    }

    if (!password) {
      this.isPasswordValid = true;
      return false;
    }
    return true;
  }
}
