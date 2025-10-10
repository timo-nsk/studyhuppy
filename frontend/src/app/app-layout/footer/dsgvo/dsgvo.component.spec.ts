import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DsgvoComponent } from './dsgvo.component';
import {ActivatedRoute} from '@angular/router';

describe('DsgvoComponent', () => {
  let component: DsgvoComponent;
  let fixture: ComponentFixture<DsgvoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DsgvoComponent],
      providers: [{
        provide: ActivatedRoute,
        useValue: {},
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DsgvoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
