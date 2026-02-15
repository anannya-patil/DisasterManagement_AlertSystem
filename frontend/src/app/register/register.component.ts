import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  email = '';
  password = '';
  confirmPassword = '';
  role = 'CITIZEN';
  errorMessage = '';
  successMessage = '';
  loading = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';
    
    console.log('Sending registration request for:', this.email);
    
    this.authService.register(this.email, this.password, this.role).subscribe({
      next: (response) => {
        console.log('Registration response received:', response);
        console.log('Response type:', typeof response);
        console.log('Response value:', response);
        
        // Check if response is the success message
        if (response === 'User registered successfully' || 
            response.includes?.('success') || 
            response === 'Registration successful') {
          
          this.successMessage = 'Registration successful! Redirecting to login...';
          this.loading = false;
          
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        } else {
          // If response is something else
          this.successMessage = response || 'Registration successful!';
          this.loading = false;
          
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        }
      },
      error: (error) => {
        console.error('Registration error details:', error);
        console.error('Error status:', error.status);
        console.error('Error response:', error.error);
        
        this.loading = false;
        
        if (error.status === 400) {
          this.errorMessage = 'Email already exists';
        } else if (error.status === 0) {
          this.errorMessage = 'Cannot connect to server. Make sure backend is running.';
        } else {
          this.errorMessage = 'Registration failed. Please try again.';
        }
      }
    });
  }
}