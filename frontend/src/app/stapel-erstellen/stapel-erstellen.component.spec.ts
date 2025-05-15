import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StapelErstellenComponent } from './stapel-erstellen.component';

describe('StapelErstellenComponent', () => {
  let component: StapelErstellenComponent;
  let fixture: ComponentFixture<StapelErstellenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StapelErstellenComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StapelErstellenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
