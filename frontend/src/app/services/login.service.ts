import {Injectable} from '@angular/core';
import {HttpClient,} from '@angular/common/http';
import {Router} from '@angular/router';
import {User} from '../model/user';
import {Observable} from 'rxjs';
import {GlobalConstants} from '../util/global-constants';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient, private router: Router) {
  }

  isLogged(): boolean {
    const login = localStorage.getItem('user');
    return login == null;
  }

  logout() {
    localStorage.removeItem('user');
  }

  login(user: User): Observable<any> {
    return this.http.post(GlobalConstants.BackendBaseURL + '/login', user);
  }

  register(user: User): Observable<any> {
    return this.http.post(GlobalConstants.BackendBaseURL + '/register', user);
  }
  confirmRegistration(token: string){
    return this.http.get<any>(GlobalConstants.BackendBaseURL + '/register/' + token);
  }

}
