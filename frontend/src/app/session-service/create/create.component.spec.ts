import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionCreateComponent } from './create.component';
import {provideHttpClient} from '@angular/common/http';

describe('CreateComponent', () => {
  let component: SessionCreateComponent;
  let fixture: ComponentFixture<SessionCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionCreateComponent],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SessionCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
