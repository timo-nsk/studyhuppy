import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TermineDetailsComponent } from './termine-details.component';

describe('TermineDetailsComponent', () => {
  let component: TermineDetailsComponent;
  let fixture: ComponentFixture<TermineDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TermineDetailsComponent]
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
