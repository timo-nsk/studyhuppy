import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LernsessionEditComponent } from './edit.component';

describe('EditComponent', () => {
  let component: LernsessionEditComponent;
  let fixture: ComponentFixture<LernsessionEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LernsessionEditComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LernsessionEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
