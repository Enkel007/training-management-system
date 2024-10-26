import { Component } from '@angular/core';
import {RouterModule} from "@angular/router";
import {Router} from "express";

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css' // Corrected property name
})
export class LoginPageComponent {


}
