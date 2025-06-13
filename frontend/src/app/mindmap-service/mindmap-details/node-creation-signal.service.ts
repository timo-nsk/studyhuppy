// signal.service.ts
import { Injectable, signal, computed, effect } from '@angular/core';
import {LoggingService} from '../../logging.service';

@Injectable({ providedIn: 'root' })
export class NodeCreationSignalService {
  log = new LoggingService("NodeCreationSignalService", "mindmap-service");
  public parentIdSignal = signal<string | null>(null)
  public showNewNodeForm = signal<boolean>(false)


  setParentId(modulId: string) {
    this.parentIdSignal.set(modulId);
    this.log.debug("Set parentId to: " + modulId);
  }

  setShowNewNodeForm() {
    let now = !this.showNewNodeForm();
    this.showNewNodeForm.set(now);
    this.log.debug("Set hideNewNodeForm to: " + now);
  }
}
