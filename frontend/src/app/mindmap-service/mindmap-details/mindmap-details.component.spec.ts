import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MindmapDetailsComponent } from './mindmap-details.component';

describe('MindmapDetailsComponent', () => {
  let component: MindmapDetailsComponent;
  let fixture: ComponentFixture<MindmapDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MindmapDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MindmapDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
