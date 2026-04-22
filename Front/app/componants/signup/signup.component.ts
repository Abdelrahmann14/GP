import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/security/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  public showPassword = false;
  public showConfirmPassword = false;

  constructor(private authService: AuthService, private router: Router) { }

  isUserNameValid = false;
  isPasswordValid = false;
  isConfirmPasswordValid = false;
  isPasswordMatched = false;
  isAgeValid = false;
  isPhoneNumberValid = false;
  isAddressValid = false;

  usernameError = '';
  passwordError = '';
  confirmPasswordError = '';
  ageError = '';
  phoneNumberError = '';
  addressError = '';
  message = '';

  ngOnInit(): void {}

  // tslint:disable-next-line:typedef
  createAccount(userName: string, password: string, confirmPassword: string, age: string, phoneNumber: string, address: string) {
    if (!this.validateInputs(userName, password, confirmPassword, age, phoneNumber, address)) {
      return;
    }

    const body = {
      username: userName,
      password,
      userDetails: {
        age: Number(age),
        phoneNumber,
        address
      }
    };

    this.authService.createAccount(body).subscribe({
      next: () => {
        this.router.navigateByUrl('/login');
      },
      error: (errorResponse) => {
        // 🔄 Reset all field errors
        this.usernameError = '';
        this.passwordError = '';
        this.confirmPasswordError = '';
        this.ageError = '';
        this.phoneNumberError = '';
        this.addressError = '';
        this.message = '';

        const bundle = errorResponse.error?.bundleMessage;

        if (Array.isArray(bundle)) {
          bundle.forEach((e: any) => {
            const field = e.field;

            if (field === 'username' || field === 'system') { this.usernameError = e.message; }
            else if (field === 'password') { this.passwordError = e.message; }
            else if (field === 'confirmPassword') { this.confirmPasswordError = e.message; }
            else if (field === 'age' || field === 'userDetails.age') { this.ageError = e.message; }
            else if (field === 'phoneNumber' || field === 'userDetails.phoneNumber') { this.phoneNumberError = e.message; }
            else if (field === 'address' || field === 'userDetails.address') { this.addressError = e.message; }
          });
        } else {
          this.message = 'An unexpected error occurred.';
        }
      }
    });
  }


  // tslint:disable-next-line:typedef
  private validateInputs(userName: string, password: string, confirmPassword: string, age: string, phoneNumber: string, address: string) {
    if (!userName) {
      this.isUserNameValid = true;
      return false;
    }

    if (!password) {
      this.isPasswordValid = true;
      return false;
    }

    if (!confirmPassword) {
      this.isConfirmPasswordValid = true;
      return false;
    }

    if (password !== confirmPassword) {
      this.isPasswordMatched = true;
      return false;
    }

    // tslint:disable-next-line:radix
    if (!age || parseInt(age) < 1 || parseInt(age) > 100) {
      this.isAgeValid = true;
      return false;
    }

    if (!phoneNumber) {
      this.isPhoneNumberValid = true;
      return false;
    }

    if (!address) {
      this.isAddressValid = true;
      return false;
    }

    return true;
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
  clearConfirmPassword() {
    this.isConfirmPasswordValid = false;
    this.isPasswordMatched = false;
    this.confirmPasswordError = '';
    this.message = '';
  }

  // tslint:disable-next-line:typedef
  clearAge() {
    this.isAgeValid = false;
    this.ageError = '';
    this.message = '';
  }

  // tslint:disable-next-line:typedef
  clearPhoneNumber() {
    this.isPhoneNumberValid = false;
    this.phoneNumberError = '';
    this.message = '';
  }

  // tslint:disable-next-line:typedef
  clearAddress() {
    this.isAddressValid = false;
    this.addressError = '';
    this.message = '';
  }

  // tslint:disable-next-line:typedef
  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  // tslint:disable-next-line:typedef
  toggleConfirmPasswordVisibility() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }
}
