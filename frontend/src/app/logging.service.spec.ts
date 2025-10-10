import { LoggingService } from './logging.service';

describe('LoggingService', () => {
  let service: LoggingService;

  beforeEach(() => {
    service = new LoggingService('TestClass', 'TestService');
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should log info message', () => {
    spyOn(console, 'info');
    service.info('Hello');
    expect(console.info).toHaveBeenCalled();
  });
});
