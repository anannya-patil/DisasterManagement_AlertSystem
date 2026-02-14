import { Component, OnInit, OnDestroy, signal } from '@angular/core';

@Component({
  selector: 'app-clock',
  standalone: true,
  templateUrl: './clock.html',
  styleUrl: './clock.css',
})
export class ClockComponent implements OnInit, OnDestroy {
  time = signal('');
  date = signal('');
  private intervalId: ReturnType<typeof setInterval> | null = null;

  ngOnInit(): void {
    const update = (): void => {
      const now = new Date();
      this.time.set(now.toLocaleTimeString('en-US', { hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false }));
      this.date.set(now.toLocaleDateString('en-US', { weekday: 'short', month: 'short', day: 'numeric', year: 'numeric' }));
    };
    update();
    this.intervalId = setInterval(update, 1000);
  }

  ngOnDestroy(): void {
    if (this.intervalId !== null) clearInterval(this.intervalId);
  }
}
