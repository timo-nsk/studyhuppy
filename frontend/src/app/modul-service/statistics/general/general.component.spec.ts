import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneralComponent } from './general.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {HeaderService} from '../../../header.service';
import {HttpHeaders} from '@angular/common/http';
import {StatisticApiService} from '../statistic.service';
import {of} from 'rxjs';

describe('GeneralComponent', () => {
  let component: GeneralComponent;
  let fixture: ComponentFixture<GeneralComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GeneralComponent, HttpClientTestingModule],
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
      {
        provide: StatisticApiService,
        useValue: {
          getGeneralStats: () => of({
            totalStudyTimePerSemester: {}, // <- wichtig!
            otherStatField: 123
          })
        }
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GeneralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
