import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KarteErstellenComponent } from './karte-erstellen.component';
import {provideHttpClient} from '@angular/common/http';

describe('KarteErstellenComponent', () => {
  let component: KarteErstellenComponent;
  let fixture: ComponentFixture<KarteErstellenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KarteErstellenComponent],
      providers: [provideHttpClient()]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KarteErstellenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
