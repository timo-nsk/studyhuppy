import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LernsessionEditComponent } from './edit.component';
import {ActivatedRoute} from '@angular/router';
import {provideHttpClient} from '@angular/common/http';

describe('EditComponent', () => {
  let component: LernsessionEditComponent;
  let fixture: ComponentFixture<LernsessionEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LernsessionEditComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              data: {
                lernsessionToEdit: {
                  id: '123',
                  title: 'Testsession',
                  duration: 60,
                  blocks: [
                    { id: 'b1', title: 'Block 1', duration: 15 },
                    { id: 'b2', title: 'Block 2', duration: 45 }
                  ]
                }
              }
            }
          }
        },
        provideHttpClient()
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LernsessionEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
