import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private apiUrl = 'http://localhost:8080/citizen';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getAlerts(): Observable<any> {
    return this.http.get(`${this.apiUrl}/alerts`, { headers: this.getHeaders() });
  }

  requestRescue(location: string, message: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/request-rescue`, 
      { location, message }, 
      { headers: this.getHeaders(), responseType: 'text' }
    );
  }

  reportIncident(type: string, location: string, description: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/report-incident`,
      { type, location, description },
      { headers: this.getHeaders(), responseType: 'text' }
    );
  }
}