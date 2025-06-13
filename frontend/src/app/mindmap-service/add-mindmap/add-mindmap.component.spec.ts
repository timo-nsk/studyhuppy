import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMindmapComponent } from './add-mindmap.component';

describe('AddMindmapComponent', () => {
  let component: AddMindmapComponent;
  let fixture: ComponentFixture<AddMindmapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddMindmapComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddMindmapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
