import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionComponent } from './session.component';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';

describe('SessionComponent', () => {
  let component: SessionComponent;
  let fixture: ComponentFixture<SessionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionComponent],
      providers: [{
        provide: ActivatedRoute,
        useValue: {},
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
