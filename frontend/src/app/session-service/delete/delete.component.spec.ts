import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SessionDeleteComponent } from './delete.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../../header.service';

describe('DeleteComponent', () => {
  let component: SessionDeleteComponent;
  let fixture: ComponentFixture<SessionDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SessionDeleteComponent],
      providers: [
      {
        provide: HeaderService,
        useValue: {
          createAuthHeader: () => new HttpHeaders({
            Authorization: 'Bearer MOCK_TOKEN',
            'Content-Type': 'application/json'
          })
        }
      },provideHttpClient()]
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
