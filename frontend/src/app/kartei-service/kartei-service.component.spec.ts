import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KarteiServiceComponent } from './kartei-service.component';

describe('KarteiServiceComponent', () => {
  let component: KarteiServiceComponent;
  let fixture: ComponentFixture<KarteiServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KarteiServiceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KarteiServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
