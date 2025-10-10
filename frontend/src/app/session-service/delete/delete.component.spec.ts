import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionDeleteComponent } from './delete.component';
import {provideHttpClient} from '@angular/common/http';

describe('DeleteComponent', () => {
  let component: SessionDeleteComponent;
  let fixture: ComponentFixture<SessionDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionDeleteComponent],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessionDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
