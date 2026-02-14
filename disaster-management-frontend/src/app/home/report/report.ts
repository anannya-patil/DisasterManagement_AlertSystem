import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './report.html',
  styleUrl: './report.css',
})
export class ReportComponent {
  type = '';
  location = '';
  description = '';
  submitted = false;

  constructor(private router: Router) {}

  onSubmit(): void {
    if (!this.type.trim() || !this.location.trim() || !this.description.trim()) return;
    this.submitted = true;
    setTimeout(() => this.router.navigate(['/home']), 1500);
  }
}
