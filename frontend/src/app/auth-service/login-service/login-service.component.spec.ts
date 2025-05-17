import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { LoginServiceComponent } from './login-service.component';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';

describe('LoginServiceComponent', () => {
  let component: LoginServiceComponent;
  let fixture: ComponentFixture<LoginServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginServiceComponent, HttpClientTestingModule],
      providers: [{
        provide: ActivatedRoute,
        useValue: {
          snapshot: {
            paramMap: {
              get: (key: string) => '123',
            },
          },
          params: of({ id: '123' }),
        },
      },]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
