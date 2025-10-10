import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MindmapDetailsComponent } from './mindmap-details.component';
import {ActivatedRoute} from '@angular/router';
import {provideHttpClient} from '@angular/common/http';
import {of} from 'rxjs';

describe('MindmapDetailsComponent', () => {
  let component: MindmapDetailsComponent;
  let fixture: ComponentFixture<MindmapDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MindmapDetailsComponent],
      providers: [{
        provide: ActivatedRoute,
        useValue: {
          paramMap: of({
            get: (key: string) => 'modul123'
          })
        },
      },
      provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MindmapDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
