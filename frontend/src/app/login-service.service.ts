import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  constructor(private http: HttpClient) { }

  public isAuthenticated = false;

  private url = 'http://localhost:8080/login';

  username: string;
  password: string;

  login(): boolean {
    const headers = new HttpHeaders();
    const token = btoa(this.username + ':' + this.password);
    headers.append('Authentication', 'Basic ' + token);
    this.http.get(this.url, {headers});
    return true;
  }

}
