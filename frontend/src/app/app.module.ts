import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { ContactTableComponent } from './contact-table/contact-table.component';
import { ContactDataComponent } from './contact-data/contact-data.component';
import { RegisterFormComponent } from './register-form/register-form.component';
import { ContactFormComponent } from './contact-form/contact-form.component';
import { ContactEditComponent } from './contact-edit/contact-edit.component';
import { MainPageWithoutLoginComponent } from './main-page-without-login/main-page-without-login.component';
import { RegistrationConfirmationComponent } from './registration-confirmation/registration-confirmation.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginFormComponent,
    ContactTableComponent,
    ContactDataComponent,
    RegisterFormComponent,
    ContactFormComponent,
    ContactEditComponent,
    MainPageWithoutLoginComponent,
    RegistrationConfirmationComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
