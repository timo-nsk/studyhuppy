import { environment } from '../environments/environment';

export class LoggingService {

  constructor(private className : string, private actualService : string) {
  }

  info(message: string): void {
    console.info(`[INFO] ${new Date().toLocaleString('de-DE')} --- [${this.actualService}] [${this.className}] : ${message}`);
  }

  error(reason: string): void {
    console.error(`[ERROR] ${new Date().toLocaleString('de-DE')} --- [${this.actualService}] [${this.className}] : ${reason}`);
  }

  debug(message: string): void {
    if (!this.isDebugMode()) return;
    console.debug(`[DEBUG] ${new Date().toLocaleString('de-DE')} --- [${this.actualService}] [${this.className}] : ${message}`);
  }

  private isDebugMode(): boolean {
    return !environment.production;
  }
}

