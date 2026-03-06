import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';  // ✅ ADD THIS IMPORT
import { DisasterService } from '../services/disaster.service';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  pendingDisasters: any[] = [];
  userEmail = '';

  constructor(
    private disasterService: DisasterService,
    private authService: AuthService,
    private router: Router  // ✅ ADD THIS
  ) {
    this.userEmail = this.authService.getUserEmail() || '';
  }

  ngOnInit(): void {
    this.loadPending();
  }

  loadPending(): void {
    this.disasterService.getPendingDisasters().subscribe({
      next: (data: any) => {
        this.pendingDisasters = data.content || data;
      },
      error: (err) => console.error(err)
    });
  }

  approve(id: number): void {
    this.disasterService.approveDisaster(id).subscribe(() => {
      this.loadPending();
    });
  }

  reject(id: number): void {
    this.disasterService.rejectDisaster(id).subscribe(() => {
      this.loadPending();
    });
  }

  resolve(id: number): void {
    this.disasterService.resolveDisaster(id).subscribe(() => {
      this.loadPending();
    });
  }

  goToDisasters(): void {
    this.router.navigate(['/disasters']);
  }

  logout(): void {
    this.authService.logout();
  }
}