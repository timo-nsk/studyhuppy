import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderMainComponent } from './header-main.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('HeaderMainComponent', () => {
  let component: HeaderMainComponent;
  let fixture: ComponentFixture<HeaderMainComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HeaderMainComponent, HttpClientTestingModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HeaderMainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
