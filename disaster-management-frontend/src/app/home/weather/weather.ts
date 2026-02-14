import { Component } from '@angular/core';

export interface WeatherData {
  temp: number;
  condition: string;
  location: string;
  humidity: number;
  wind: number;
  icon: string;
}

@Component({
  selector: 'app-weather',
  standalone: true,
  templateUrl: './weather.html',
  styleUrl: './weather.css',
})
export class WeatherComponent {
  weather: WeatherData = {
    temp: 22,
    condition: 'Partly cloudy',
    location: 'Metro City',
    humidity: 65,
    wind: 12,
    icon: '⛅',
  };
}
