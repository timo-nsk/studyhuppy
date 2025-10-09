import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KontaktComponent } from './kontakt.component';
import {provideHttpClient} from '@angular/common/http';

describe('KontaktComponent', () => {
  let component: KontaktComponent;
  let fixture: ComponentFixture<KontaktComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KontaktComponent],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KontaktComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
