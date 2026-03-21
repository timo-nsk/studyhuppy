import {TestBed} from '@angular/core/testing';
import {ModuleApiService} from './module-api.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {HeaderService} from '../../header.service';
import {HttpHeaders} from '@angular/common/http';

describe('ModulApiService', () => {
  let moduleApiService: ModuleApiService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ModuleApiService, HeaderService, {
        provide: HeaderService,
        useValue: {
          createAuthHeader: () => new HttpHeaders({
            Authorization: 'Bearer MOCK_TOKEN',
            'Content-Type': 'application/json'
          })
        }
      }]
    })

    moduleApiService = TestBed.inject(ModuleApiService);
    httpTestingController = TestBed.inject(HttpTestingController);
  })

  it('should get seconds of a modul by its fachId', () => {
    let seconds: number | undefined;
    let fachId = '11111111-1111-1111-1111-111111111111';
    moduleApiService.getSeconds(fachId).subscribe({
      next: (res) => { seconds = res; }
    })
    let request = httpTestingController.expectOne(`http://localhost:9502/api/modul/v1/get-seconds?fachId=${fachId}`);
    request.flush(1000);

    expect(seconds).toEqual(1000);
  })
})
