import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionStartComponent } from './start.component';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../../header.service';

describe('StartComponent', () => {
  let component: SessionStartComponent;
  let fixture: ComponentFixture<SessionStartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionStartComponent],
      providers: [{
        provide: ActivatedRoute,
        useValue: {
          snapshot: {
            paramMap: {
              get: (sessionId: string) => 'session_uuid',
            },
          },
          params: of({ id: '123' }),
        },
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

    fixture = TestBed.createComponent(SessionStartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
