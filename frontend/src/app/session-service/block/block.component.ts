import {Component, effect, Input} from '@angular/core';
import {NgForOf} from '@angular/common';
import {Block} from '../session-domain';
import {Modul} from '../../modul-service/module/domain';
import {BlockManager} from './block-manager.service';
import {FormsModule} from '@angular/forms';
import {PomodoroSignalService} from '../create/pomodoro-signal.service';

@Component({
  selector: 'Lernblock',
  imports: [NgForOf, FormsModule],
  templateUrl: './block.component.html',
  standalone: true,
  styleUrl: './block.component.scss'
})
export class BlockComponent{
  @Input() index!: number;
  @Input() block!: Block;
  @Input() module : Modul[] = []
  @Input() blockManager : BlockManager | undefined;

  constructor(private pomodoroSignalService : PomodoroSignalService) {
    // Lauscht auf Signaländerungen
    effect(() => {
      if (this.pomodoroSignalService.pomodoroSignal() === 1) {
        this.setPomodoroValues()
      }
    });
  }

  setModulOfBlock(event : Event): void {
    const select = event.target as HTMLSelectElement;
    const modulId = select.value;
    this.block.setModulId(modulId)

    let modulName = ""

    for (let i = 0; i < this.module.length; i++) {
      if(this.module[i].fachId == modulId) {
        modulName = this.module[i].name
        break
      }
    }

    this.block.setModulName(modulName)
  }

  setLernzeitOfBlock(event : Event): void {
    const select = event.target as HTMLSelectElement;
    const minutes = Number(select.value);
    this.block.setLernzeitSeconds(minutes * 60);
  }

  setPauseOfBlock(event : Event): void {
    const select = event.target as HTMLSelectElement;
    const minutes = Number(select.value);
    this.block.setPausezeitSeconds(minutes * 60)
  }

  removeBlock(index : number) {
    this.blockManager?.removeBlock(index)
  }

  /**
   * Setzt die Lernzeit auf 25 Minuten und die Pausenzeit auf 5 Minuten für alle Blöcke, wenn sich das Signal im PomodoroSignalService ändert.
   */
  setPomodoroValues() {
    const selects = ["lernzeit-select", "pausen-select"];

    for (let selectId of selects) {
      const selectHtmlElement = document.getElementsByClassName(selectId);
      for (let i = 0; i < selectHtmlElement.length; i++) {
        const select = selectHtmlElement[i] as HTMLSelectElement;
        for (let j = 0; j < select.options.length; j++) {
          const option = select.options[j];
          if (selectId === selects[0]) {
            option.selected = parseInt(option.value) === 25;
          } else if (selectId === selects[1]) {
            option.selected = parseInt(option.value) === 5;
          }
        }
      }
    }
  }
}
