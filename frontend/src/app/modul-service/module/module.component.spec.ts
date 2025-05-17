import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ModuleComponent } from './module.component';
import {ModuleService} from './module-service';

describe('ModuleComponent', () => {
  let component: ModuleComponent;
  let fixture: ComponentFixture<ModuleComponent>;
  let service: ModuleService;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ModuleComponent, ModuleService],
    });

    service = TestBed.inject(ModuleService);
    httpMock = TestBed.inject(HttpTestingController);
    fixture = TestBed.createComponent(ModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should update overall seconds of the modul and the seconds learned when starting to learn', () => {
    let seconds = 10

    let res = component.updateSeconds(seconds)

    expect(res).toBe(11)
    expect(component.sessionSecondsLearned).toBe(1)
  })
});
