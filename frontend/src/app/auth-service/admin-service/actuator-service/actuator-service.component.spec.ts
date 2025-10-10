import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActuatorServiceComponent } from './actuator-service.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../../../header.service';

describe('ActuatorServiceComponent', () => {
  let component: ActuatorServiceComponent;
  let fixture: ComponentFixture<ActuatorServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActuatorServiceComponent],
      providers: [
      {
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

    fixture = TestBed.createComponent(ActuatorServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
