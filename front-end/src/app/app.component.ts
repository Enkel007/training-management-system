import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {LoginPageComponent} from "./Components/login-page/login-page.component";
import {WelcomePageComponent} from "./Components/welcome-page/welcome-page.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    LoginPageComponent,
    WelcomePageComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'front-end';
}
