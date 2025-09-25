import {Injectable} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Block} from '../session-domain';

@Injectable({
  providedIn: "root"
})
export class BlockFormManager {

  private blockFormList: FormGroup[];

  constructor() {this.blockFormList = [];}

  /**
   * Fügt ein neues Block-Formular zur blockFormList hinzu.
   */
  appendBlockForm(): void {
    let blockForm = new FormGroup({
      fachId: new FormControl(''),
      modulId: new FormControl('', Validators.required),
      modulName: new FormControl('', Validators.required),
      lernzeitSeconds: new FormControl(300, [Validators.required, Validators.min(300)]),
      pausezeitSeconds: new FormControl(300, [Validators.required, Validators.min(300)])
    });
    this.blockFormList.push(blockForm);
  }

  /**
   * Fügt ein neues Block-Formular mit Daten eines Block-Interface zur blockFormList hinzu.
   * @param block : Block - das Block-Interface, dessen Daten in das Formular übernommen werden sollen.
   */
  appendBlockFormWithData(block : Block): void {
    let blockForm = new FormGroup({
      fachId: new FormControl(block.fachId),
      modulId: new FormControl(block.modulId, Validators.required),
      modulName: new FormControl(block.modulName, Validators.required),
      lernzeitSeconds: new FormControl(block.lernzeitSeconds, [Validators.required, Validators.min(300)]),
      pausezeitSeconds: new FormControl(block.pausezeitSeconds, [Validators.required, Validators.min(300)])
    });
    this.blockFormList.push(blockForm);
  }

  /**
   * Entfernt das Block-Formular an der angegebenen Indexposition aus der blockFormList.
   * @param index : number - die Indexposition des zu entfernenden Block-Formulars.
   */
  removeBlockForm(index: number): void {
    if(this.hasAtLeastOneBlockForm()) return

    if (index >= 0 && index < this.blockFormList.length) {
      this.blockFormList.splice(index, 1);
    } else {
      console.error(`Index ${index} is out of bounds for block list.`);
    }
  }

  /**
   * Überprüft, ob mindestens ein Block-Formular in der blockFormList vorhanden ist,
   * damit auf der UI mindestens ein Block angezeigt wird.
   */
  hasAtLeastOneBlockForm(): boolean {
    return this.blockFormList.length == 1;
  }

  /**
   * Setzt die blockFormList zurück, indem, bis auf ein Formular, alle Block-Formulare.
   */
  resetBlockFormList() {
    for(let i = this.blockFormList.length - 1; i > 0; i--) {
      this.removeBlockForm(i)
    }

    this.blockFormList[0].reset()
  }

  /**
   * Konvertiert die blockFormList in ein Array von Block-Objekten.
   */
  toBlockArray() : Block[] {
    let blocks : Block[] = []

    for (let form of this.blockFormList) {
      let b = new Block(
        form.get('modulName')?.value,
        form.get('lernzeitSeconds')?.value,
        form.get('pausezeitSeconds')?.value,
        form.get('fachId')?.value,
        form.get('modulId')?.value);
      blocks.push(b)
    }

    return blocks
  }

  get blockForms(): FormGroup[] {
    return this.blockFormList;
  }

  set blocks(blocks: FormGroup[]) {
    this.blockFormList = blocks;
  }

  printBlockForms(): void {
    for (let form of this.blockFormList) {
      console.log(form.value)
    }
  }
}
