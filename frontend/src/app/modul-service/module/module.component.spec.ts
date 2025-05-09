import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModuleComponent } from './module.component';

describe('ModuleServiceComponent', () => {
  let component: ModuleComponent;
  let fixture: ComponentFixture<ModuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModuleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
