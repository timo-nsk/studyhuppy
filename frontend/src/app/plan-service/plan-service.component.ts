import {Component, inject} from '@angular/core';
import {NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';
import {SnackbarService} from '../snackbar.service';


@Component({
  selector: 'app-plan-service',
  imports: [
    NgIf,
    RouterLink
  ],
  templateUrl: './plan-service.component.html',
  standalone: true,
  styleUrls: ['./plan-service.component.scss', '../general.scss', '../button.scss']
})
export class PlanServiceComponent {
  snackbarService = inject(SnackbarService)
  titel = "blub"

  deleteLernplan() {
    // delete lernplan by id
    console.log("deleted")
    this.snackbarService.openSuccess(`Lernplan "${this.titel}" gelöscht`)
  }
}
