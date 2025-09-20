import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-edit',
  imports: [],
  templateUrl: './edit.component.html',
  styleUrl: './edit.component.scss'
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
