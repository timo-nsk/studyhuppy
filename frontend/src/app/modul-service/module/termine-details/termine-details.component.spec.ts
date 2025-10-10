import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TermineDetailsComponent } from './termine-details.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {ActivatedRoute, convertToParamMap} from '@angular/router';
import {of} from 'rxjs';
import {HeaderService} from '../../../header.service';

describe('TermineDetailsComponent', () => {
  let component: TermineDetailsComponent;
  let fixture: ComponentFixture<TermineDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TermineDetailsComponent],
      providers: [{
        provide: ActivatedRoute,
        useValue: {
          snapshot: {
            paramMap: convertToParamMap({ id: '123' }),
            queryParamMap: convertToParamMap({ foo: 'bar' })
          },
          params: of({ id: '123' }),
          queryParams: of({ foo: 'bar' })
        }
      },
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

    fixture = TestBed.createComponent(TermineDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
