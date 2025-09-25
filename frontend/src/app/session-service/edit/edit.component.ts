import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {SessionFormComponent} from '../session-form/session-form.component';
import {Session} from '../session-domain';

@Component({
  selector: 'app-edit',
  imports: [SessionFormComponent],
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss', '../../general.scss', '../../button.scss', '../../color.scss']
})
export class LernsessionEditComponent implements OnInit{
  route = inject(ActivatedRoute);
  lernsessionToEdit!: Session;

  ngOnInit(): void {
    this.lernsessionToEdit = this.route.snapshot.data['lernsessionToEdit'];
  }

}
