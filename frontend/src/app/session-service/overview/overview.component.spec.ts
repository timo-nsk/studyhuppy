import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionOverviewComponent } from './overview.component';
import {provideHttpClient} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';

describe('OverviewComponent', () => {
  let component: SessionOverviewComponent;
  let fixture: ComponentFixture<SessionOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionOverviewComponent],
      providers: [{
        provide: ActivatedRoute,
        useValue: {}
      },
      provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessionOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
