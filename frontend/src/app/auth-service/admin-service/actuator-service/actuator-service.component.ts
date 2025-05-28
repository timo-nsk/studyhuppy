import {Component, inject, OnInit} from '@angular/core';
import {ActuatorApiService} from './actuator.api.service';
import {SystemHealth} from './system-health';

@Component({
  selector: 'app-actuator-service',
  imports: [],
  templateUrl: './actuator-service.component.html',
  standalone: true,
  styleUrl: './actuator-service.component.scss'
})
export class ActuatorServiceComponent implements OnInit{
  apiService = inject(ActuatorApiService)
  systemHealth : SystemHealth[] = [];

  ngOnInit(): void {
    this.apiService.getSystemHealth().subscribe({
      next: (data : SystemHealth[]) => {
        this.systemHealth = data
        console.log("got system health")
        console.log(this.systemHealth)
      },
      error: (err) => {
        console.log(err)
      }
    })
  }
}
