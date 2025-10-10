import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDbServiceComponent } from './user-db-service.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../../../../header.service';

describe('UserDbServiceComponent', () => {
  let component: UserDbServiceComponent;
  let fixture: ComponentFixture<UserDbServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserDbServiceComponent],
      providers: [{
        provide: HeaderService,
        useValue: {
          createAuthHeader: () => new HttpHeaders({
            Authorization: 'Bearer MOCK_TOKEN',
            'Content-Type': 'application/json'
          })
        }
      },
      provideHttpClient()]
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
