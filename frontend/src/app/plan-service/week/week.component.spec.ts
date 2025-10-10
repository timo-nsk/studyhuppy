import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanWeekComponent } from './week.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';
import {HeaderService} from '../../header.service';

describe('WeekComponent', () => {
  let component: PlanWeekComponent;
  let fixture: ComponentFixture<PlanWeekComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanWeekComponent],
      providers: [{
        provide: ActivatedRoute,
        useValue: {},
      },
      {
        provide: HeaderService,
        useValue: {
          createAuthHeader: () => new HttpHeaders({
            Authorization: 'Bearer MOCK_TOKEN',
            'Content-Type': 'application/json'
          })
        }
      },
      provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlanWeekComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
