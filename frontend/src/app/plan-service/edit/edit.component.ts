import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {PlanFormComponent} from '../plan-form/plan-form.component';
import {Lernplan} from '../plan-domain';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-edit',
  imports: [PlanFormComponent, NgIf],
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss', '../../general.scss', '../../button.scss', '../../color.scss']
})
export class PlanEditComponent implements OnInit{
  route = inject(ActivatedRoute);
  lernplanId! : string;
  lernplanToEdit!: Lernplan;

  ngOnInit(): void {
    // Dank Resolver wird die Navigation erst abgeschlossen, wenn die Daten geladen sind.
    // Dadurch ist das Objekt hier garantiert verfügbar und nicht undefined.
    this.lernplanToEdit = this.route.snapshot.data['lernplanToEdit'];
  }
}
