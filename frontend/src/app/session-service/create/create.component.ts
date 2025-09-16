import {Component, inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';
import {BlockComponent} from '../block/block.component';
import {SessionApiService} from '../session-api.service';
import {Block, Session} from '../session-domain';
import {SnackbarService} from '../../snackbar.service';
import {Modul} from '../../modul-service/module/domain';
import {ModuleApiService} from '../../modul-service/module/module-api.service';
import {BlockManager} from '../block/block-manager.service';

@Component({
  selector: 'app-session-create',
  imports: [BlockComponent, FormsModule, NgForOf, ReactiveFormsModule, NgIf],
  templateUrl: './create.component.html',
  standalone: true,
  styleUrls: ['./create.component.scss', '../../general.scss', '../../button.scss', '../../forms.scss', '../../color.scss']
})
export class SessionCreateComponent implements OnInit{
  snackbarService = inject(SnackbarService)
  sessionApiService = inject(SessionApiService)
  session: Session = {} as Session
  module : Modul[] = []
  modulService = inject(ModuleApiService)
  blockManager = inject(BlockManager)
  currentBlockList : Block[] = []
  invalidBlocks : boolean[] = []
  sessionForm : FormGroup = new FormGroup({
    sessionTitel: new FormControl("", Validators.required),
    sessionBeschreibung: new FormControl("")
  })

  ngOnInit(): void {
    this.modulService.getAllModulesByUsername().subscribe(
      {
        next: data => { this.module = data }
      }
    )
    this.setSessionConfigData()
  }

  setSessionConfigData() : void {
    const block = this.blockManager.initDefaultBlock()
    this.blockManager.appendBlock(block)

    this.session = new Session("", "", this.blockManager.blocks);
    this.currentBlockList = this.blockManager.blocks
  }

  getBlock(index : number) : Block {
    return this.blockManager.getBlock(index)
  }

  saveSession(): void {
    this.blockManager.printBlocks()
    if(this.blockManager.isValidBlockList() && this.sessionForm.valid) {

      this.session.titel = this.sessionForm.get('sessionTitel')?.value;
      this.session.beschreibung = this.sessionForm.get('sessionBeschreibung')?.value;
      this.session.blocks = this.blockManager.blocks

      console.log(this.session)

      this.sessionApiService.saveSession(this.session).subscribe({
        next: (response) => {
          this.snackbarService.openSuccess("Session erfolgreich gespeichert.");
          this.clearComponent()
          this.setSessionConfigData();
        },
        error: (error) => {
          this.snackbarService.openError("Fehler beim Speichern der Session. Bitte versuchen Sie es erneut.");
          console.error("Error saving session:", error);
        }
      })
    } else {
      this.sessionForm.markAllAsTouched()
      this.invalidBlocks = this.blockManager.validateBlocks()
      console.error("Session ist ungültig. Bitte überprüfen Sie die Eingaben.");
    }
  }

  appendBlock() {
    const block = this.blockManager.initDefaultBlock()
    this.blockManager.appendBlock(block)
    // currentBlockList wird genutzt, damit die View aktualisiert wird
    this.currentBlockList = this.blockManager.blocks
  }

  clearComponent() {
    this.blockManager.clearList()
    this.currentBlockList = []
    this.invalidBlocks = []
    this.sessionForm.reset()
  }
}
