import { Injectable, signal, computed } from '@angular/core';
import { Router } from '@angular/router';

export type Role = 'admin' | 'responder' | 'citizen';

const TOKEN_KEY = 'dm_token';
const ROLE_KEY = 'dm_role';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly tokenSignal = signal<string | null>(this.getStoredToken());
  private readonly roleSignal = signal<Role | null>(this.getStoredRole());

  readonly token = this.tokenSignal.asReadonly();
  readonly role = this.roleSignal.asReadonly();
  readonly isLoggedIn = computed(() => !!this.tokenSignal());

  constructor(private router: Router) {}

  private getStoredToken(): string | null {
    return typeof localStorage !== 'undefined' ? localStorage.getItem(TOKEN_KEY) : null;
  }

  private getStoredRole(): Role | null {
    const r = typeof localStorage !== 'undefined' ? localStorage.getItem(ROLE_KEY) : null;
    if (r === 'admin' || r === 'responder' || r === 'citizen') return r;
    return null;
  }

  setRole(role: Role): void {
    if (typeof localStorage !== 'undefined') localStorage.setItem(ROLE_KEY, role);
    this.roleSignal.set(role);
  }

  getStoredRoleOnly(): Role | null {
    const r = typeof localStorage !== 'undefined' ? localStorage.getItem(ROLE_KEY) : null;
    if (r === 'admin' || r === 'responder' || r === 'citizen') return r;
    return null;
  }

  login(email: string, phone: string): void {
    const mockToken = `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.${btoa(JSON.stringify({ email, phone, role: this.getStoredRoleOnly() }))}.mock`;
    if (typeof localStorage !== 'undefined') localStorage.setItem(TOKEN_KEY, mockToken);
    this.tokenSignal.set(mockToken);
    const role = this.getStoredRoleOnly();
    if (role) this.roleSignal.set(role);
    this.router.navigate(['/home']);
  }

  logout(): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.removeItem(TOKEN_KEY);
    }
    this.tokenSignal.set(null);
    this.router.navigate(['/role']);
  }

  register(email: string, phone: string, location: string, role: Role): void {
    if (typeof localStorage !== 'undefined') localStorage.setItem(ROLE_KEY, role);
    this.roleSignal.set(role);
    this.router.navigate(['/login']);
  }
}
