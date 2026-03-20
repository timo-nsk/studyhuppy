import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulServiceComponent } from './modul-service.component';
import {ActivatedRoute} from '@angular/router';
import {of} from 'rxjs';
import {By} from '@angular/platform-browser';

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

  it('should render three pagination items', () => {
    let items = fixture.debugElement.queryAll(By.css('[data-testid="page-a"]'));
    console.log(items);
    expect(items.length).toEqual(3);
  })
});
