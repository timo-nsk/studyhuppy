import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddMindmapComponent } from './add-mindmap.component';
import {HttpHeaders, provideHttpClient} from '@angular/common/http';
import {HeaderService} from '../../header.service';
import {HttpClientTestingModule, provideHttpClientTesting} from '@angular/common/http/testing';

describe('AddMindmapComponent', () => {
  let component: AddMindmapComponent;
  let fixture: ComponentFixture<AddMindmapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddMindmapComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        {
          provide: HeaderService,
          useValue: {
            createAuthHeader: () => new HttpHeaders({
              Authorization: 'Bearer MOCK_TOKEN',
              'Content-Type': 'application/json'
            })
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AddMindmapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
