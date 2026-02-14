import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService, Role } from '../../core/services/auth.service';
import { WeatherComponent } from '../weather/weather';
import { AlertsComponent } from '../alerts/alerts';
import { ClockComponent } from '../clock/clock';
import { EmergencyContactComponent } from '../emergency-contact/emergency-contact';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    RouterLink,
    WeatherComponent,
    AlertsComponent,
    ClockComponent,
    EmergencyContactComponent,
  ],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class DashboardComponent {
  constructor(protected auth: AuthService) {}

  get role(): Role | null {
    return this.auth.role();
  }
}
