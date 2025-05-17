import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddModuleComponent } from './add-module.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('AddModuleComponent', () => {
  let component: AddModuleComponent;
  let fixture: ComponentFixture<AddModuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddModuleComponent, HttpClientTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
