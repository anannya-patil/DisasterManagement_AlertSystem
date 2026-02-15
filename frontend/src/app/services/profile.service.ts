import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl = 'http://localhost:8080/profiles';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  createProfile(profileData: any): Observable<any> {
    return this.http.post(this.apiUrl, profileData, { headers: this.getHeaders() });
  }

  getAllProfiles(): Observable<any> {
    return this.http.get(this.apiUrl, { headers: this.getHeaders() });
  }

  getProfileByRegion(region: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/region/${region}`, { headers: this.getHeaders() });
  }
}