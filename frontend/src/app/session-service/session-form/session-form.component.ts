import {Component, inject, Input, OnInit} from '@angular/core';
import {BlockComponent} from "../block/block.component";
import {FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {SnackbarService} from '../../snackbar.service';
import {SessionApiService} from '../session-api.service';
import {PomodoroSignalService} from '../create/pomodoro-signal.service';
import {Block, Session} from '../session-domain';
import {Modul} from '../../modul-service/module/domain';
import {ModuleApiService} from '../../modul-service/module/module-api.service';
import {BlockManager} from '../block/block-manager.service';

@Component({
  selector: 'app-session-form',
    imports: [
        BlockComponent,
        FormsModule,
        NgForOf,
        NgIf,
        ReactiveFormsModule
    ],
  templateUrl: './session-form.component.html',
  styleUrls: ['./session-form.component.scss', '../../general.scss', '../../button.scss', '../../color.scss']
})
export class SessionFormComponent implements  OnInit{
  snackbarService = inject(SnackbarService)
  sessionApiService = inject(SessionApiService)
  pomodoroSignalService = inject(PomodoroSignalService)
  formBuilder : FormBuilder = inject(FormBuilder)
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

  @Input() lernsessionToEdit! : Session

  ngOnInit(): void {
    this.modulService.getAllModulesByUsername().subscribe(
      { next: data => { this.module = data } }
    )

    if(this.hasLernsessionToEdit()) {
      this.prepareSessionForEdit()
    } else {
      this.prepareSessionForCreate()
    }
  }

  prepareSessionForEdit() : void {
    this.blockManager.blocks = this.lernsessionToEdit.blocks
    this.currentBlockList = this.blockManager.blocks
    this.fillFormForEdit()
  }

  prepareSessionForCreate() : void {
    const block = this.blockManager.initDefaultBlock()
    this.blockManager.appendBlock(block)

    this.session = new Session("", "", this.blockManager.blocks);
    this.currentBlockList = this.blockManager.blocks
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
          this.prepareSessionForCreate();
        },
        error: (error) => {
          this.snackbarService.openError("Fehler beim Speichern der Session. Bitte versuchen Sie es erneut.");
          console.error("Error saving session:", error);
        }
      })
    } else {
      this.sessionForm.markAllAsTouched()
      this.invalidBlocks = this.blockManager.validateBlocks()
      console.log("invalid blockList:, ", this.currentBlockList)
    }
  }

  appendBlock() {
    const block = this.blockManager.initDefaultBlock()
    this.blockManager.appendBlock(block)
    // currentBlockList wird genutzt, damit die View aktualisiert wird
    this.currentBlockList = this.blockManager.blocks
  }

  createBlockForm() : FormGroup {
    return this.formBuilder.group({
      fachId: '',
      modulId: '',
      modulName: '',
      lernzeitSeconds: [300, [Validators.required, Validators.min(300)]],
      pausezeitSeconds: [300, [Validators.required, Validators.min(300)]]
    });
  }

  clearComponent() {
    this.blockManager.clearList()
    this.currentBlockList = []
    this.invalidBlocks = []
    this.sessionForm.reset()
  }

  /**
   * Setzt alle Blöcke auf lernzeitSeconds=1500 und pausezeitSeconds=500.
   * und signalisiert die Änderung im PomodoroSignalService
   */
  setPomodoroTimes() {
    console.log("before", JSON.parse(JSON.stringify(this.currentBlockList)));

    this.currentBlockList = this.currentBlockList.map(b =>
      new Block("", 25 * 60, 5 * 60, b.fachId, "")
    );

    this.pomodoroSignalService.changeSignal()

    console.log("after", JSON.parse(JSON.stringify(this.currentBlockList)));
  }

  hasLernsessionToEdit() : boolean {
    return this.lernsessionToEdit !== undefined && this.lernsessionToEdit !== null;
  }

  /**
   * Befüllt das Form-Objekt mit den Daten aus dem lernplanToEdit-Objekt
   */
  fillFormForEdit() {
    this.sessionForm.patchValue({sessionTitel: this.lernsessionToEdit.titel})
    this.sessionForm.patchValue({sessionBeschreibung: this.lernsessionToEdit.beschreibung})

  }
}
