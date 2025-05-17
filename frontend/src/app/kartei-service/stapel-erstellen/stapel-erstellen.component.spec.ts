import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StapelErstellenComponent } from './stapel-erstellen.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('StapelErstellenComponent', () => {
  let component: StapelErstellenComponent;
  let fixture: ComponentFixture<StapelErstellenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StapelErstellenComponent, HttpClientTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StapelErstellenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
