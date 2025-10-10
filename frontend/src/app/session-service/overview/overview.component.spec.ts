import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionOverviewComponent } from './overview.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';
import {HeaderService} from '../../header.service';

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

    fixture = TestBed.createComponent(SessionOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
