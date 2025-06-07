// signal.service.ts
import { Injectable, signal, computed, effect } from '@angular/core';
import {LoggingService} from '../../logging.service';

@Injectable({ providedIn: 'root' })
export class NodeCreationSignalService {
  log = new LoggingService("NodeCreationSignalService", "mindmap-service");
  public parentIdSignal = signal<string | null>(null)


  setParentId(modulId: string) {
    this.parentIdSignal.set(modulId);
    this.log.debug("Set parentId to: " + modulId);
  }
}
