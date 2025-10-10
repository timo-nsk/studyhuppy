import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanCreateComponent } from './create.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../../header.service';

describe('CreateComponent', () => {
  let component: PlanCreateComponent;
  let fixture: ComponentFixture<PlanCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanCreateComponent],
      providers: [{
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

    fixture = TestBed.createComponent(PlanCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
