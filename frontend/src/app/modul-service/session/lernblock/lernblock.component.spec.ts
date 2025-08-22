import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LernblockComponent } from './lernblock.component';

describe('LernblockComponent', () => {
  let component: LernblockComponent;
  let fixture: ComponentFixture<LernblockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LernblockComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LernblockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
