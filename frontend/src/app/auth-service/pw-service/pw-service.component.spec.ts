import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PwServiceComponent } from './pw-service.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {AuthApiService} from '../auth.service';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';

describe('PwServiceComponent', () => {
  let component: PwServiceComponent;
  let fixture: ComponentFixture<PwServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PwServiceComponent, HttpClientTestingModule],
      providers: [AuthApiService,
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
        }]

    })
    .compileComponents();

    fixture = TestBed.createComponent(PwServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
