import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService, Role } from '../../core/services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterComponent implements OnInit {
  email = '';
  phone = '';
  location = '';
  role: Role | null = null;
  roleLabel = '';

  constructor(private auth: AuthService) {}

  ngOnInit(): void {
    this.role = this.auth.getStoredRoleOnly();
    this.roleLabel = this.role ? this.role.charAt(0).toUpperCase() + this.role.slice(1) : '';
  }

  onSubmit(): void {
    if (!this.email.trim() || !this.phone.trim() || !this.location.trim() || !this.role) return;
    this.auth.register(this.email.trim(), this.phone.trim(), this.location.trim(), this.role);
  }
}
