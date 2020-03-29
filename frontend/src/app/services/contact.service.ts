import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Contact} from '../model/contact';
import {GlobalConstants} from '../util/global-constants';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private http: HttpClient) { }
   loadAll(): Observable<Array<Contact>>{
    return this.http.get<Array<Contact>>(GlobalConstants.BackendBaseURL + '/contacts');
   }
   loadById(id: number): Observable<Contact>{
    return this.http.get<Contact>(GlobalConstants.BackendBaseURL + '/contacts/' + id );
   }
   addContact(contact: Contact): Observable<any>{
    return this.http.post(GlobalConstants.BackendBaseURL + '/contacts', contact);
   }
   deleteContact(id: number): Observable<any>{
    return this.http.delete(GlobalConstants.BackendBaseURL + '/contacts/' + id);
   }
   modifyContacts(contact: Contact): Observable<any>{
    return this.http.put(GlobalConstants.BackendBaseURL + '/contacts/' + contact.id, contact);
   }
}
