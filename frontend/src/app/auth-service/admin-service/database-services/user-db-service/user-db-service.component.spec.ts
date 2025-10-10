import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDbServiceComponent } from './user-db-service.component';
import {provideHttpClient} from '@angular/common/http';

describe('UserDbServiceComponent', () => {
  let component: UserDbServiceComponent;
  let fixture: ComponentFixture<UserDbServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserDbServiceComponent],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserDbServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
