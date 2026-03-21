import {TestBed} from '@angular/core/testing';
import {ModuleApiService} from './module-api.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {HeaderService} from '../../header.service';
import {HttpHeaders} from '@angular/common/http';
import {Modul} from './domain';

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

  it('should get all module by fachsemester of a user', () => {
    let receivedModules: { [key: number]: Modul[] };

    moduleApiService.getAllModuleByFachsemester().subscribe({
      next: (res) => { receivedModules = res; }
    })

    let request = httpTestingController.expectOne('http://localhost:9502/api/modul/v1/get-all-module-by-fachsemester')
    request.flush(
      {
        1: [
          {
            id: null,
            fachId: 'id',
            name: 'Dummy Modul 1',
            secondsLearned: 1000,
            kreditpunkte: {
              anzahlPunkte: 5,
              kontaktzeitStunden: 30,
              selbststudiumStunden: 60
            },
            username: 'testuser',
            active: true,
            semesterstufe: 1,
            semester: undefined,
            klausurDate: new Date(),
            lerntage: undefined,
            modultermine: undefined,
          }
        ],
        2: [
          {
            id: null,
            fachId: 'id',
            name: 'Dummy Modul 2',
            secondsLearned: 1000,
            kreditpunkte: {
              anzahlPunkte: 5,
              kontaktzeitStunden: 30,
              selbststudiumStunden: 60
            },
            username: 'testuser',
            active: true,
            semesterstufe: 1,
            semester: undefined,
            klausurDate: new Date(),
            lerntage: undefined,
            modultermine: undefined,
          }
        ]
      }
    )

    expect(receivedModules[1][0].name).toEqual('Dummy Modul 1');
    expect(receivedModules[2][0].name).toEqual('Dummy Modul 2');
  })
})
