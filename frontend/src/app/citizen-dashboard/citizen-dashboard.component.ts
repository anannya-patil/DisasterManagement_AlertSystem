import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AlertService } from '../services/alert.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-citizen-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './citizen-dashboard.component.html',
  styleUrls: ['./citizen-dashboard.component.css']
})
export class CitizenDashboardComponent implements OnInit {
  alerts: any[] = [];
  userEmail = '';
  
  // Rescue request
  location = '';
  message = '';
  rescueResponse = '';
  
  // Incident report
  incidentType = '';
  incidentLocation = '';
  incidentDescription = '';
  incidentResponse = '';

  constructor(
    private alertService: AlertService,
    private authService: AuthService,
    private router: Router
  ) {
    this.userEmail = this.authService.getUserEmail() || '';
  }

  ngOnInit(): void {
    this.loadAlerts();
  }

  loadAlerts(): void {
    this.alertService.getAlerts().subscribe({
      next: (data) => {
        this.alerts = data.alerts || [];
      },
      error: (error) => {
        console.error('Error loading alerts', error);
      }
    });
  }

  requestRescue(): void {
    this.alertService.requestRescue(this.location, this.message).subscribe({
      next: (response) => {
        this.rescueResponse = response;
        setTimeout(() => this.rescueResponse = '', 5000);
        this.location = '';
        this.message = '';
      },
      error: (error) => {
        console.error('Error requesting rescue', error);
        this.rescueResponse = 'Failed to send request';
      }
    });
  }

  reportIncident(): void {
    this.alertService.reportIncident(
      this.incidentType, 
      this.incidentLocation, 
      this.incidentDescription
    ).subscribe({
      next: (response) => {
        this.incidentResponse = response;
        setTimeout(() => this.incidentResponse = '', 5000);
        this.incidentType = '';
        this.incidentLocation = '';
        this.incidentDescription = '';
      },
      error: (error) => {
        console.error('Error reporting incident', error);
        this.incidentResponse = 'Failed to report incident';
      }
    });
  }

  logout(): void {
    this.authService.logout();
  }
}