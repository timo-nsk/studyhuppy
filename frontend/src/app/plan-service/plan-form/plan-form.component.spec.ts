import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanFormComponent } from './plan-form.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../../header.service';

describe('PlanFormComponent', () => {
  let component: PlanFormComponent;
  let fixture: ComponentFixture<PlanFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanFormComponent],
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

    fixture = TestBed.createComponent(PlanFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
