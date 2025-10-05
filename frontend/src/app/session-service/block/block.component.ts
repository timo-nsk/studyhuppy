import {Component, effect, Input} from '@angular/core';
import {NgForOf} from '@angular/common';
import {Modul} from '../../modul-service/module/domain';
import {FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {PomodoroSignalService} from '../create/pomodoro-signal.service';
import {BlockFormManager} from './blockform-manager.service';

@Component({
  selector: 'Lernblock',
  imports: [NgForOf, FormsModule, ReactiveFormsModule],
  templateUrl: './block.component.html',
  standalone: true,
  styleUrl: './block.component.scss'
})
export class BlockComponent{
  @Input() index!: number;
  @Input() blockForm!: FormGroup;
  @Input() module : Modul[] = []
  @Input() blockFormManager : BlockFormManager | undefined;

  constructor(private pomodoroSignalService : PomodoroSignalService) {
    // Lauscht auf Signaländerungen
    effect(() => {
      if (this.pomodoroSignalService.pomodoroSignal() === 1) {
        this.setPomodoroValues()
      }
    });
  }

  setModulName(event : Event): void {
    const select = event.target as HTMLSelectElement;
    const modulId = select.value;
    const modul = this.module.find(m => m.fachId == modulId);
    let modulName = "";
    if (modul) {
      modulName = modul.name;
    }

    this.blockForm.patchValue({modulName: modulName})
  }

  removeBlock(index : number) {
    this.blockFormManager?.removeBlockForm(index)
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
