import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActuatorServiceComponent } from './actuator-service.component';
import {provideHttpClient} from '@angular/common/http';

describe('ActuatorServiceComponent', () => {
  let component: ActuatorServiceComponent;
  let fixture: ComponentFixture<ActuatorServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActuatorServiceComponent],
      providers: [provideHttpClient()]
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
