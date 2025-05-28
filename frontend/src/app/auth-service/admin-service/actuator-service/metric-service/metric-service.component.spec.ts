import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MetricServiceComponent } from './metric-service.component';

describe('MetricServiceComponent', () => {
  let component: MetricServiceComponent;
  let fixture: ComponentFixture<MetricServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MetricServiceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MetricServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
