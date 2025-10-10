import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanServiceComponent } from './plan-service.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../header.service';

describe('PlanServiceComponent', () => {
  let component: PlanServiceComponent;
  let fixture: ComponentFixture<PlanServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanServiceComponent],
      providers: [
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

    fixture = TestBed.createComponent(PlanServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
