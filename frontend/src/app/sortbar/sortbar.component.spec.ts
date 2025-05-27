import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SortbarComponent } from './sortbar.component';

describe('SortbarComponent', () => {
  let component: SortbarComponent;
  let fixture: ComponentFixture<SortbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SortbarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SortbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
