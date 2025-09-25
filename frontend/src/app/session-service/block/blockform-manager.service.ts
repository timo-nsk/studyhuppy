import {Injectable} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Injectable({
  providedIn: "root"
})
export class BlockFormManager {

  private blockFormList: FormGroup[];

  constructor() {this.blockFormList = [];}

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

  removeBlock(index: number): void {
    if(this.hasAtLeastOneBlockForm()) return

    if (index >= 0 && index < this.blockFormList.length) {
      this.blockFormList.splice(index, 1);
    } else {
      console.error(`Index ${index} is out of bounds for block list.`);
    }
  }

  getBlockForm(index: number): FormGroup{
    return this.blockForms[index]
  }

  get blockForms(): FormGroup[] {
    return this.blockFormList;
  }

  set blocks(blocks: FormGroup[]) {
    this.blockFormList = blocks;
  }

  hasAtLeastOneBlockForm(): boolean {
    return this.blockFormList.length == 1;
  }

  clearBlockFormList() {
    this.blockFormList = []
  }
}
