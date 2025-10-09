import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppLayoutComponent } from './app-layout.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {AuthApiService} from '../auth-service/auth.service';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';
import {HeaderService} from '../header.service';
import {HttpHeaders} from '@angular/common/http';

describe('AppLayoutComponent', () => {
  let component: AppLayoutComponent;
  let fixture: ComponentFixture<AppLayoutComponent>;

  beforeEach(async () => {
    const dummyToken = [
      btoa(JSON.stringify({ alg: 'HS256', typ: 'JWT' })),
      btoa(JSON.stringify({ sub: 'testuser', authorities: ['ROLE_ADMIN'] })),
      'signature'
    ].join('.');
    localStorage.setItem('auth_token', dummyToken);

    await TestBed.configureTestingModule({
      imports: [AppLayoutComponent, HttpClientTestingModule],
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

    fixture = TestBed.createComponent(AppLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
