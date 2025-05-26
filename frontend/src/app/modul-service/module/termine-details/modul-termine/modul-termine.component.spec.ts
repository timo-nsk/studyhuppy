import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulTermineComponent } from './modul-termine.component';

describe('ModulTermineComponent', () => {
  let component: ModulTermineComponent;
  let fixture: ComponentFixture<ModulTermineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModulTermineComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModulTermineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
