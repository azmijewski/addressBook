import {MainPageComponent} from './main-page/main-page.component';
import {LoginFormComponent} from './login-form/login-form.component';
import {RegisterFormComponent} from './register-form/register-form.component';
import {ContactDataComponent} from './contact-data/contact-data.component';
import {ContactFormComponent} from './contact-form/contact-form.component';
import {RegistrationConfirmationComponent} from './registration-confirmation/registration-confirmation.component';
import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ContactTableComponent} from './contact-table/contact-table.component';

const Routes = [
  {
    path: '',
    component: MainPageComponent
  },
  {
    path: 'contacts',
    component: ContactTableComponent
  },
  {
    path: 'login',
    component: LoginFormComponent
  },
  {
    path: 'register',
    component: RegisterFormComponent
  },
  {
    path: 'contacts/:id',
    component: ContactDataComponent,
  },
  {
    path: 'add',
    component: ContactFormComponent
  },
  {
    path: 'register/:token',
    component: RegistrationConfirmationComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(Routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
