import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ImpressumComponent } from './impressum.component';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';

describe('ImpressumComponent', () => {
  let component: ImpressumComponent;
  let fixture: ComponentFixture<ImpressumComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ImpressumComponent],
      providers: [{
        provide: ActivatedRoute,
        useValue: {},
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ImpressumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
