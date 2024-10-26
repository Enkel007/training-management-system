import { Routes } from '@angular/router';
import path from "node:path";
import {LoginPageComponent} from "./Components/login-page/login-page.component";

export const routes: Routes = [
  {
    path: '',
    component: LoginPageComponent
  }
];

