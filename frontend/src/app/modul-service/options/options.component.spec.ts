import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OptionsComponent } from './options.component';
import {HttpClient} from '@angular/common/http';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('OptionsComponent', () => {
  let component: OptionsComponent;
  let fixture: ComponentFixture<OptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OptionsComponent, HttpClientTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
