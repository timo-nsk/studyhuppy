import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PausenblockComponent } from './pausenblock.component';

describe('PausenblockComponent', () => {
  let component: PausenblockComponent;
  let fixture: ComponentFixture<PausenblockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PausenblockComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PausenblockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
