import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StapelDetailsComponent } from './stapel-details.component';

describe('StapelDetailsComponent', () => {
  let component: StapelDetailsComponent;
  let fixture: ComponentFixture<StapelDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StapelDetailsComponent]
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
