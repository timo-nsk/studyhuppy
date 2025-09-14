import {Injectable} from '@angular/core';
import {Block} from '../session-domain';

@Injectable({
  providedIn: "root"
})
export class BlockManager {

  private blockList: Block[];

  constructor() {
    this.blockList = [];
  }

  initDefaultBlock() : Block {
    return new Block("", 300, 300, "uuid-placeholder");
  }

  get blocks(): Block[] {
    return this.blockList;
  }

  appendBlock(block: Block): void {
    this.blockList.push(block);
  }

  removeBlock(index: number): void {
    if(this.hasAtLeastOneBlock()) return

    if (index >= 0 && index < this.blockList.length) {
      this.blockList.splice(index, 1);
    } else {
      console.error(`Index ${index} is out of bounds for block list.`);
    }
    this.printBlocks()
  }

  getBlock(index: number): Block{
    return this.blocks[index]
  }

  printBlocks(): void {
    console.log(`Block List (size=${this.blockList.length}):`);
    for( let block of this.blockList) {
      block.printBlock()
    }
  }

  hasAtLeastOneBlock(): boolean {
    return this.blockList.length == 1;
  }
}
