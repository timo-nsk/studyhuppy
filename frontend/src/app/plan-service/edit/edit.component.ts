import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {PlanFormComponent} from '../plan-form/plan-form.component';

@Component({
  selector: 'app-edit',
  imports: [PlanFormComponent],
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss', '../../general.scss', '../../button.scss', '../../color.scss']
})
export class PlanEditComponent implements OnInit{
  route = inject(ActivatedRoute);
  lernplanId! : string;

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.lernplanId = params['lernplanId']
      console.log(this.lernplanId)
    });
  }

}
