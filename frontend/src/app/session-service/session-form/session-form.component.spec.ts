import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionFormComponent } from './session-form.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../../header.service';

describe('SessionFormComponent', () => {
  let component: SessionFormComponent;
  let fixture: ComponentFixture<SessionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionFormComponent],
      providers: [{
        provide: HeaderService,
        useValue: {
          createAuthHeader: () => new HttpHeaders({
            Authorization: 'Bearer MOCK_TOKEN',
            'Content-Type': 'application/json'
          })
        }
      },provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
