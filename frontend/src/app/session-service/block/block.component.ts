import {Component, Input} from '@angular/core';
import {NgForOf} from '@angular/common';
import {Block} from '../session-domain';
import {Modul} from '../../modul-service/module/domain';
import {BlockManager} from './block-manager.service';

@Component({
  selector: 'Lernblock',
  imports: [NgForOf],
  templateUrl: './block.component.html',
  standalone: true,
  styleUrl: './block.component.scss'
})
export class BlockComponent{
  @Input() index!: number;
  @Input() block!: Block;
  @Input() module : Modul[] = []
  @Input() blockManager : BlockManager | undefined;

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
}
