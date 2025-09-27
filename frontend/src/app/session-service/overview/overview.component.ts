import {Component, inject, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {SessionApiService} from '../session-api.service';
import {Session} from '../session-domain';
import {SnackbarService} from '../../snackbar.service';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-overview',
  imports: [NgForOf, NgIf, RouterLink],
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss', '../../general.scss', '../../button.scss']
})
export class SessionOverviewComponent implements OnInit{
  sessionApiService = inject(SessionApiService);
  snackbarService = inject(SnackbarService)
  lernsessions : Session[] | undefined

  ngOnInit(): void {
    this.sessionApiService.getSessions().subscribe({
      next: (data : Session[]) =>{
        this.lernsessions = data
      },
      error: err => {
        console.error("Error fetching sessions", err);
      }
    })
  }

  deleteSession(lernsessionId: string | undefined, titel: string) {
    this.sessionApiService.deleteSession(lernsessionId).subscribe({
      next: (response) => {
        console.log(response)
        this.ngOnInit()
        this.snackbarService.openSuccess(`Lernsession "${titel}" gelöscht`)
      },
      error: (error) => {
        console.error('Error deleting lernsession:', error);
        this.snackbarService.openError('Fehler beim Löschen der Lernsession');
      }
    })
  }

  hasLernsessions() : boolean {
    return this.lernsessions !== undefined
  }
}
