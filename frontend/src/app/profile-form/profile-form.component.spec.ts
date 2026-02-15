import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ProfileService } from '../services/profile.service';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile-form.component.html',
  styleUrls: ['./profile-form.component.css']
})
export class ProfileFormComponent {
  fullName = '';
  phone = '';
  region = '';
  errorMessage = '';
  successMessage = '';
  loading = false;

  constructor(
    private profileService: ProfileService,
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit() {
    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';
    
    const profileData = {
      fullName: this.fullName,
      phone: this.phone,
      region: this.region
    };
    
    this.profileService.createProfile(profileData).subscribe({
      next: (response) => {
        this.successMessage = 'Profile created successfully!';
        this.loading = false;
        
        const role = this.authService.getUserRole();
        if (role === 'ADMIN') {
          setTimeout(() => this.router.navigate(['/admin']), 2000);
        } else if (role === 'RESPONDER') {
          setTimeout(() => this.router.navigate(['/responder']), 2000);
        } else {
          setTimeout(() => this.router.navigate(['/citizen']), 2000);
        }
      },
      error: (error) => {
        this.loading = false;
        if (error.status === 400) {
          this.errorMessage = 'Profile already exists for this user';
        } else {
          this.errorMessage = 'Profile creation failed. Please try again.';
        }
        console.error('Profile creation error', error);
      }
    });
  }
}