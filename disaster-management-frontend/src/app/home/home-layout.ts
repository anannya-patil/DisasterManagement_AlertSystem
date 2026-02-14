import { Component, signal } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService, Role } from '../core/services/auth.service';

interface SidebarItem {
  path: string;
  label: string;
  icon: string;
}

@Component({
  selector: 'app-home-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './home-layout.html',
  styleUrl: './home-layout.css',
})
export class HomeLayoutComponent {
  sidebarOpen = signal(false);

  constructor(protected auth: AuthService) {}

  get role(): Role | null {
    return this.auth.role();
  }

  get roleLabel(): string {
    const r = this.role;
    return r ? r.charAt(0).toUpperCase() + r.slice(1) : '';
  }

  get navSubtitle(): string {
    const r = this.role;
    if (r === 'admin') return 'Manage users, alerts, and reports';
    if (r === 'responder') return 'Respond to incidents and assignments';
    return 'View alerts and report incidents';
  }

  get sidebarItems(): SidebarItem[] {
    const r = this.role;
    const base: SidebarItem[] = [
      { path: '/home', label: 'Dashboard', icon: '◉' },
      { path: '/home/report', label: 'Report Incident', icon: '✎' },
    ];
    if (r === 'admin') {
      return [
        ...base,
        { path: '/home/alerts-manage', label: 'Manage Alerts', icon: '🔔' },
        { path: '/home/users', label: 'Users', icon: '👥' },
      ];
    }
    if (r === 'responder') {
      return [
        ...base,
        { path: '/home/assignments', label: 'My Assignments', icon: '📋' },
      ];
    }
    return base;
  }

  openSidebar(): void {
    this.sidebarOpen.set(true);
  }

  closeSidebar(): void {
    this.sidebarOpen.set(false);
  }

  toggleSidebar(): void {
    this.sidebarOpen.update((v) => !v);
  }

  logout(): void {
    this.auth.logout();
  }
}
