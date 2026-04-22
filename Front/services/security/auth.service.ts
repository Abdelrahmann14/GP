import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  baseUrl = 'http://localhost:9091/auth/';

  constructor(private http: HttpClient) { }


  createAccount(userData: any): Observable<any> {
    return this.http.post<any>(this.baseUrl + 'sign-up', userData);
  }


  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.baseUrl + 'login', {username, password}).pipe(
      map(
        response => response
      )
    );
  }
  // tslint:disable-next-line:typedef
  isUserLogin(){
    return sessionStorage.getItem('token');
  }
  // tslint:disable-next-line:typedef
  logout() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('userName');
    sessionStorage.removeItem('roles');
  }
  isUserAdmin(){
    return sessionStorage.getItem('roles') && sessionStorage.getItem('roles').includes('ADMIN');
  }
}
