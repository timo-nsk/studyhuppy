import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulDetailsComponent } from './modul-details.component';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';
import {provideHttpClient} from '@angular/common/http';

describe('ModulDetailsComponent', () => {
  let component: ModulDetailsComponent;
  let fixture: ComponentFixture<ModulDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModulDetailsComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            queryParams: of({
              modulId: '123',
              name: 'TestModul',
              secondsLearned: 3600,
              kreditpunkte: 5,
              kontaktzeitStunden: 10,
              selbststudiumStunden: 20,
              semesterstufe: 3,
              semesterTyp: 'SOMMERSEMESTER',
              lerntage: ['Montag', 'Mittwoch']
            })
          }
        },
        provideHttpClient()
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModulDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
