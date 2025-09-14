import {Component, inject, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgForOf} from '@angular/common';
import {BlockComponent} from '../block/block.component';
import {SessionApiService} from '../session-api.service';
import {Block, Session} from '../session-domain';
import {SnackbarService} from '../../snackbar.service';
import {Modul} from '../../modul-service/module/domain';
import {ModuleApiService} from '../../modul-service/module/module-api.service';
import {BlockManager} from '../block/block-manager.service';

@Component({
  selector: 'app-session-create',
  imports: [BlockComponent, FormsModule, NgForOf, ReactiveFormsModule],
  templateUrl: './create.component.html',
  standalone: true,
  styleUrls: ['./create.component.scss', '../../general.scss', '../../button.scss', '../../forms.scss', '../../color.scss']
})
export class SessionCreateComponent implements OnInit{
  snackbarService = inject(SnackbarService)
  sessionApiService = inject(SessionApiService)
  anzahlBloecke : number = 1;
  session: any
  module : Modul[] = []
  modulService = inject(ModuleApiService)
  blockManager = inject(BlockManager)
  currentBlockList : Block[] = []

  ngOnInit(): void {
    this.modulService.getAllModulesByUsername().subscribe(
      {
        next: data => { this.module = data }
      }
    )
    this.setSessionConfigData()
  }

  setSessionConfigData() : void {
    for(let i = 0; i < this.anzahlBloecke; i++) {
      const block = this.blockManager.initDefaultBlock()
      this.blockManager.appendBlock(block)
    }
    this.session = new Session("", "", this.blockManager.blocks);
    this.currentBlockList = this.blockManager.blocks
  }

  getBlock(index : number) : Block {
    return this.blockManager.getBlock(index)
  }

  saveSession(): void {
    if(this.session.validSession()) {
      this.sessionApiService.saveSession(this.session).subscribe({
        next: (response) => {
          this.snackbarService.openSuccess("Session erfolgreich gespeichert.");
          this.setSessionConfigData();
        },
        error: (error) => {
          this.snackbarService.openError("Fehler beim Speichern der Session. Bitte versuchen Sie es erneut.");
          console.error("Error saving session:", error);
        }
      })
    } else {
      console.error("Session ist ungültig. Bitte überprüfen Sie die Eingaben.");
    }
  }

  appendBlock() {
    const block = this.blockManager.initDefaultBlock()
    this.blockManager.appendBlock(block)
    // currentBlockList wird genutzt, damit die View aktualisiert wird
    this.currentBlockList = this.blockManager.blocks
    this.blockManager.printBlocks()
  }
}
