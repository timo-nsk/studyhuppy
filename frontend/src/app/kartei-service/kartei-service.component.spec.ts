import { ComponentFixture, TestBed } from '@angular/core/testing';
import { KarteiServiceComponent } from './kartei-service.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { HeaderService } from '../header.service'; // Pfad ggf. anpassen
import { HttpHeaders } from '@angular/common/http';

describe('KarteiServiceComponent', () => {
  let component: KarteiServiceComponent;
  let fixture: ComponentFixture<KarteiServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        KarteiServiceComponent,
        HttpClientTestingModule
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get: (key: string) => '123',
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
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(KarteiServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
