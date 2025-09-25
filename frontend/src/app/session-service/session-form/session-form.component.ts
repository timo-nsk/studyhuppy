import {Component, inject, Input, OnDestroy, OnInit} from '@angular/core';
import {BlockComponent} from "../block/block.component";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {SnackbarService} from '../../snackbar.service';
import {SessionApiService} from '../session-api.service';
import {PomodoroSignalService} from '../create/pomodoro-signal.service';
import {Block, Session} from '../session-domain';
import {Modul} from '../../modul-service/module/domain';
import {ModuleApiService} from '../../modul-service/module/module-api.service';
import {BlockFormManager} from '../block/blockform-manager.service';

@Component({
  selector: 'app-session-form',
    imports: [BlockComponent, FormsModule, NgForOf, NgIf, ReactiveFormsModule],
  templateUrl: './session-form.component.html',
  styleUrls: ['./session-form.component.scss', '../../general.scss', '../../button.scss', '../../color.scss']
})
export class SessionFormComponent implements  OnInit, OnDestroy{
  @Input() lernsessionToEdit! : Session
  snackbarService = inject(SnackbarService)
  sessionApiService = inject(SessionApiService)
  pomodoroSignalService = inject(PomodoroSignalService)
  modulService = inject(ModuleApiService)

  module : Modul[] = []
  blockFormManager : BlockFormManager;
  sessionMetaDataForm : FormGroup = new FormGroup({
    sessionTitel: new FormControl("", Validators.required),
    sessionBeschreibung: new FormControl("")
  })

  constructor(blockFormManager : BlockFormManager) {
    this.blockFormManager = blockFormManager
  }

  ngOnInit(): void {
    this.modulService.getAllModulesByUsername().subscribe({ next: data => { this.module = data } })

    if(this.hasLernsessionToEdit()) this.fillFormForEdit()
    else this.appendBlock()
  }

  ngOnDestroy(): void {
    this.destroyBlockManager()
  }

  /**
   * Validiert die Formulardaten und speichert die Lernsession über den SessionApiService.
   */
  saveLernsession(): void {
    if(this.isValidFormData()) {

      const session = this.prepareSession()

      this.sessionApiService.saveSession(session).subscribe({
        next: () => {
          this.snackbarService.openSuccess("Session erfolgreich gespeichert.");
          this.clearComponent()
        },
        error: (error) => {
          this.snackbarService.openError("Fehler beim Speichern der Session. Bitte versuchen Sie es erneut.");
          console.error("Error saving session:", error);
        }
      })
    }
  }

  appendBlock() {
    this.blockFormManager.appendBlockForm()
  }

  /**
   * Setzt das Formular und die Blockliste zurück, wenn eine Session erfolgreich gespeichert wurde.
   */
  clearComponent() {
    this.blockFormManager.resetBlockFormList()
    this.sessionMetaDataForm.reset()
  }

  destroyBlockManager() : void {
    this.blockFormManager.blocks = []
  }

  /**
   * Setzt alle Blöcke auf lernzeitSeconds=1500 und pausezeitSeconds=500.
   * und signalisiert die Änderung im PomodoroSignalService
   */
  setPomodoroTimes() {
    // TODO refactor
  }

  /**
   * Überprüft, ob eine Lernsession zum Bearbeiten vorhanden ist, damit die Formularfelder entsprechend befüllt werden können.
   */
  hasLernsessionToEdit() : boolean {
    return this.lernsessionToEdit !== undefined && this.lernsessionToEdit !== null;
  }

  /**
   * Befüllt das Form-Objekt mit den Daten aus dem lernplanToEdit-Objekt
   */
  fillFormForEdit() {
    this.sessionMetaDataForm.patchValue({sessionTitel: this.lernsessionToEdit.titel})
    this.sessionMetaDataForm.patchValue({sessionBeschreibung: this.lernsessionToEdit.beschreibung})

    for(let block of this.lernsessionToEdit.blocks) {
      this.blockFormManager.appendBlockFormWithData(block)
    }
  }

  /**
   * Validiert die Formulardaten der Session-Metadaten und aller Blöcke.
   */
  private isValidFormData() {
    let invalid = 0

    if (this.sessionMetaDataForm.invalid) {
      this.sessionMetaDataForm.markAllAsTouched()
      invalid++
    }

    for(let form of this.blockFormManager.blockForms) {
      if(form.invalid) {
        form.markAllAsTouched()
        invalid++
      }
    }

    return invalid === 0;
  }

  /**
   * Bereitet die Session-Daten aus den Formularen vor, damit der SessionApiService sie absenden kann.
   */
  private prepareSession() : Session {
    let session  = {} as Session
    session.titel = this.sessionMetaDataForm.get('sessionTitel')?.value;
    session.beschreibung = this.sessionMetaDataForm.get('sessionBeschreibung')?.value;
    session.blocks = this.blockFormManager.toBlockArray()
    return session
  }
}
