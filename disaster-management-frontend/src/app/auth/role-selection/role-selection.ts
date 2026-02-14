import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, Role } from '../../core/services/auth.service';

@Component({
  selector: 'app-role-selection',
  standalone: true,
  templateUrl: './role-selection.html',
  styleUrl: './role-selection.css',
})
export class RoleSelectionComponent {
  constructor(
    private auth: AuthService,
    private router: Router,
  ) {}

  selectRole(role: Role): void {
    this.auth.setRole(role);
    this.router.navigate(['/login']);
  }
}
