import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PwServiceComponent } from './pw-service.component';

describe('PwServiceComponent', () => {
  let component: PwServiceComponent;
  let fixture: ComponentFixture<PwServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PwServiceComponent]
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
