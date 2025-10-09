import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlanEditComponent } from './edit.component';
import { ActivatedRoute } from '@angular/router';
import { HttpHeaders, provideHttpClient } from '@angular/common/http';
import { HeaderService } from '../../header.service';
import { Lernplan } from '../plan-domain';

describe('PlanEditComponent', () => {
  let component: PlanEditComponent;
  let fixture: ComponentFixture<PlanEditComponent>;

  const mockLernplan: Lernplan = {
    fachId: 'uuid',
    username: 'a',
    titel: 't',
    tagesListe: [],
    active: false
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanEditComponent],
      providers: [
        provideHttpClient(),
        {
          provide: HeaderService,
          useValue: {
            createAuthHeader: () =>
              new HttpHeaders({
                Authorization: 'Bearer MOCK_TOKEN',
                'Content-Type': 'application/json',
              }),
          },
        },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              data: { lernplanToEdit: mockLernplan }
            }
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(PlanEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have lernplanToEdit set from resolver', () => {
    expect(component.lernplanToEdit).toEqual(mockLernplan);
  });
});
