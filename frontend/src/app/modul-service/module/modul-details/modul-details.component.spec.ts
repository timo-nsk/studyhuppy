import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulDetailsComponent } from './modul-details.component';

describe('ModulDetailsComponent', () => {
  let component: ModulDetailsComponent;
  let fixture: ComponentFixture<ModulDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModulDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModulDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
