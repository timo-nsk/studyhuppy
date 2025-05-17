import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulServiceComponent } from './modul-service.component';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';

describe('ModulServiceComponent', () => {
  let component: ModulServiceComponent;
  let fixture: ComponentFixture<ModulServiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModulServiceComponent],
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
      }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModulServiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
