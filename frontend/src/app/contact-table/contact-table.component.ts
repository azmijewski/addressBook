import { Component, OnInit } from '@angular/core';
import {ContactService} from '../services/contact.service';
import {Contact} from '../model/contact';
import {Router} from '@angular/router';

@Component({
  selector: 'app-contact-table',
  templateUrl: './contact-table.component.html',
  styleUrls: ['./contact-table.component.css']
})
export class ContactTableComponent implements OnInit {

  isDataReceived: boolean;
  contacts: Array<Contact>;

  constructor(private contactService: ContactService, private router: Router) { }

  ngOnInit(): void {
    this.isDataReceived = false;
    this.contactService.loadAll().subscribe(data => this.contacts = data, error => {}, () => {});
  }
  private completeLoading() {
    this.isDataReceived = true;
  }

  addContact() {
    this.router.navigateByUrl('/add');
  }
}
