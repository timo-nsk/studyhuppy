import {Component, inject, OnInit} from '@angular/core';
import {AddTerminComponent} from './add-termin/add-termin.component';
import {NgIf} from '@angular/common';
import {ModulTermineComponent} from './modul-termine/modul-termine.component';
import {ModultermineApiService} from './termine.service';
import {ActivatedRoute} from '@angular/router';
import {Modultermin} from '../domain';
import {SortbarComponent} from '../../../sortbar/sortbar.component';
import {LoggingService} from '../../../logging.service';

@Component({
  selector: 'app-termine-details',
  imports: [AddTerminComponent, NgIf, ModulTermineComponent, SortbarComponent],
  templateUrl: './termine-details.component.html',
  standalone: true,
  styleUrls: ['./termine-details.component.scss', '../../../general.scss', '../../../forms.scss']
})
export class TermineDetailsComponent implements OnInit{
  log = new LoggingService("TermineDetailsComponent", "modul-service")
  termineService = inject(ModultermineApiService)
  route = inject(ActivatedRoute)

  hideAddForm: boolean = true
  modulId! : string
  modultermine: Modultermin[] = [];
  sortItems : { titel: string; feld: string; }[] = [
    {titel: "", feld: ""},
    { titel: "Titel", feld: "terminName"},
    {titel: "Datum", feld: "startDate"}]


  showForm() {
    this.hideAddForm = !this.hideAddForm
    this.initComponentData()
  }

  ngOnInit(): void {
    this.initComponentData()
  }

  initComponentData() {
    this.modulId = this.route.snapshot.queryParamMap.get('fachId')!;

    this.termineService.getTermine(this.modulId).subscribe({
      next: (data) => {
        this.modultermine = data
        this.log.debug(`Got termine data=${this.modultermine}`)
      },
      error: (err) => {
        this.log.error(err)
      }
    })
  }

  reloadData() {
    this.initComponentData()
  }

  sortByAttribut(event: [string, boolean]) {
    const sortAttribut = event[0]
    const asc = event[1]

    if (sortAttribut == 'terminName') {
      if(asc) {
        this.modultermine.sort((a, b) => a.terminName.localeCompare(b.terminName));
      } else {
        this.modultermine.sort((a, b) => b.terminName.localeCompare(a.terminName));
      }
    } else if(sortAttribut == 'startDate') {
      if(asc) {
        this.modultermine.sort((a, b) => new Date(a.startDate).getTime() - new Date(b.startDate).getTime());
      } else {
        this.modultermine.sort((a, b) => new Date(b.startDate).getTime() - new Date(a.startDate).getTime());
      }
    }
  }
}
