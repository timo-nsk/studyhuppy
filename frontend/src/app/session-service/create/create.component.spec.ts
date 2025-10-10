import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionCreateComponent } from './create.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../../header.service';

describe('CreateComponent', () => {
  let component: SessionCreateComponent;
  let fixture: ComponentFixture<SessionCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionCreateComponent],
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

    fixture = TestBed.createComponent(SessionCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
