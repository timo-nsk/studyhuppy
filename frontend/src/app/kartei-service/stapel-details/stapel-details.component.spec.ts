import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StapelDetailsComponent } from './stapel-details.component';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';
import {KarteiApiService} from '../kartei.api.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {HeaderService} from '../../header.service';
import {HttpHeaders} from '@angular/common/http';

describe('StapelDetailsComponent', () => {
  let component: StapelDetailsComponent;
  let fixture: ComponentFixture<StapelDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StapelDetailsComponent, HttpClientTestingModule],
      providers: [KarteiApiService,
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of({
              get: (key: string) => {
                if (key === 'fachId') return '42';
                return null;
              },
            }),
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
        },]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StapelDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
