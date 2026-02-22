import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DisasterService } from '../services/disaster.service';
import { DisasterEvent } from '../models/disaster.model';

@Component({
  selector: 'app-edit-disaster',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-disaster.component.html',
  styleUrls: ['./edit-disaster.component.css']
})
export class EditDisasterComponent implements OnInit {
  disasterId: number = 0;
  disaster: DisasterEvent | null = null;
  
  loading = true;
  saving = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private disasterService: DisasterService
  ) {}

  ngOnInit(): void {
    // Get disaster ID from URL
    this.route.params.subscribe(params => {
      this.disasterId = +params['id']; // The '+' converts string to number
      this.loadDisaster();
    });
  }

  loadDisaster(): void {
    this.loading = true;
    this.errorMessage = '';
    
    this.disasterService.getDisasterById(this.disasterId).subscribe({
      next: (data) => {
        this.disaster = data;
        this.loading = false;
        console.log('Loaded disaster:', this.disaster);
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = 'Failed to load disaster details.';
        console.error('Error loading disaster:', error);
      }
    });
  }

  saveChanges(): void {
    if (!this.disaster) return;
    
    this.saving = true;
    this.errorMessage = '';
    this.successMessage = '';
    
    this.disasterService.updateDisaster(this.disasterId, this.disaster).subscribe({
      next: (updatedDisaster) => {
        this.saving = false;
        this.successMessage = 'Disaster updated successfully!';
        
        // Redirect back to disasters page after 2 seconds
        setTimeout(() => {
          this.router.navigate(['/disasters']);
        }, 2000);
      },
      error: (error) => {
        this.saving = false;
        this.errorMessage = 'Failed to update disaster. Please try again.';
        console.error('Error updating disaster:', error);
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/disasters']);
  }
}