import { Component } from '@angular/core';

export type AlertSeverity = 'critical' | 'high' | 'medium' | 'info';

export interface AlertItem {
  id: string;
  title: string;
  severity: AlertSeverity;
  time: string;
  area: string;
}

@Component({
  selector: 'app-alerts',
  standalone: true,
  templateUrl: './alerts.html',
  styleUrl: './alerts.css',
})
export class AlertsComponent {
  alerts: AlertItem[] = [
    { id: '1', title: 'Flash flood warning', severity: 'critical', time: '5 min ago', area: 'North District' },
    { id: '2', title: 'Strong winds expected', severity: 'high', time: '1 hr ago', area: 'Coastal Zone' },
    { id: '3', title: 'Heavy rain advisory', severity: 'medium', time: '2 hrs ago', area: 'Metro City' },
    { id: '4', title: 'Weather update', severity: 'info', time: '3 hrs ago', area: 'Region' },
  ];
}
