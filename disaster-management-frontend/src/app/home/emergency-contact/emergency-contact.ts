import { Component } from '@angular/core';

export interface ContactItem {
  name: string;
  number: string;
  type: string;
}

@Component({
  selector: 'app-emergency-contact',
  standalone: true,
  templateUrl: './emergency-contact.html',
  styleUrl: './emergency-contact.css',
})
export class EmergencyContactComponent {
  contacts: ContactItem[] = [
    { name: 'Emergency hotline', number: '911', type: 'National' },
    { name: 'Disaster response', number: '+1 (555) 100-2000', type: '24/7' },
    { name: 'Local authority', number: '+1 (555) 200-3000', type: 'City' },
  ];
}
