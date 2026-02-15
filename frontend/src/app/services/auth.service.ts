import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';
  private tokenKey = 'auth_token';
  private userRoleKey = 'user_role';
  private userEmailKey = 'user_email';
  
  private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasToken());
  isLoggedIn$ = this.isLoggedInSubject.asObservable();

  constructor(private http: HttpClient, private router: Router) {}

  // ✅ FIXED: Added responseType: 'text'
  register(email: string, password: string, role: string): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/register`, 
      { email, password, role }, 
      { responseType: 'text' }  // ← THIS FIXES THE ISSUE
    );
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/login`, 
      { email, password }, 
      { responseType: 'text' }
    ).pipe(
      tap(token => {
        this.setToken(token);
        this.setUserEmail(email);
        if (email.includes('admin')) {
          this.setUserRole('ADMIN');
        } else if (email.includes('responder')) {
          this.setUserRole('RESPONDER');
        } else {
          this.setUserRole('CITIZEN');
        }
      })
    );
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
    this.isLoggedInSubject.next(true);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  setUserRole(role: string): void {
    localStorage.setItem(this.userRoleKey, role);
  }

  getUserRole(): string | null {
    return localStorage.getItem(this.userRoleKey);
  }

  setUserEmail(email: string): void {
    localStorage.setItem(this.userEmailKey, email);
  }

  getUserEmail(): string | null {
    return localStorage.getItem(this.userEmailKey);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userRoleKey);
    localStorage.removeItem(this.userEmailKey);
    this.isLoggedInSubject.next(false);
    this.router.navigate(['/login']);
  }

  private hasToken(): boolean {
    return !!localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return this.hasToken();
  }
}