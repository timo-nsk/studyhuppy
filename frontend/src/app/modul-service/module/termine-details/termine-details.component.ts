import {Component, inject, OnInit} from '@angular/core';
import {AddTerminComponent} from './add-termin/add-termin.component';
import {NgIf} from '@angular/common';
import {ModulTermineComponent} from './modul-termine/modul-termine.component';
import {ModultermineApiService} from './termine.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-termine-details',
  imports: [AddTerminComponent, NgIf, ModulTermineComponent],
  templateUrl: './termine-details.component.html',
  standalone: true,
  styleUrls: ['./termine-details.component.scss', '../../../general.scss', '../../../forms.scss']
})
export class TermineDetailsComponent implements OnInit{

  termineService = inject(ModultermineApiService)
  route = inject(ActivatedRoute)

  hideAddForm: boolean = true
  modulId! : string
  modultermine: any;


  showForm() {
    this.hideAddForm = !this.hideAddForm
  }

  ngOnInit(): void {
    this.initComponentData()
    console.log(this.modulId)
  }

  initComponentData() {
    this.route.queryParams.subscribe(params => {
      this.modulId = params['fachId']
    });

    this.termineService.getTermine(this.modulId).subscribe({
      next: (data) => {
        this.modultermine = data
        console.log(this.modultermine)
      },
      error: (err) => {
        console.log(err.status)
      }
    })
  }
}
