import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulServiceComponent } from './modul-service.component';

describe('ModulServiceComponent', () => {
  let component: ModulServiceComponent;
  let fixture: ComponentFixture<ModulServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModulServiceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModulServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
