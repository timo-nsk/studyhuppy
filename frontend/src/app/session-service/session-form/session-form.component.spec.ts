import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionFormComponent } from './session-form.component';
import {provideHttpClient} from '@angular/common/http';

describe('SessionFormComponent', () => {
  let component: SessionFormComponent;
  let fixture: ComponentFixture<SessionFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionFormComponent],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessionFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
