import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MindmapServiceComponent } from './mindmap-service.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../header.service';

describe('MindmapServiceComponent', () => {
  let component: MindmapServiceComponent;
  let fixture: ComponentFixture<MindmapServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MindmapServiceComponent],
      providers: [
      {
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

    fixture = TestBed.createComponent(MindmapServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
