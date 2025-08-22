import {Component, inject, OnInit} from '@angular/core';
import {ModuleApiService} from '../../module/module-api.service';
import {Modul} from '../../module/domain';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'Lernblock',
  imports: [
    NgForOf
  ],
  templateUrl: './lernblock.component.html',
  standalone: true,
  styleUrl: './lernblock.component.scss'
})
export class LernblockComponent implements OnInit{
  module : Modul[] = []
  modulService = inject(ModuleApiService)

  ngOnInit(): void {
    this.modulService.getAllModulesByUsername().subscribe(
      {
        next: data => {
          this.module = data
          console.log(this.module)
        }
      }
    )
  }
}
