import {Component, inject, OnInit} from '@angular/core';
import {NgForOf, NgIf} from '@angular/common';
import {MindmapApiService} from '../mindmap-api.service';
import {ModuleApiService} from '../../modul-service/module/module-api.service';
import {LoggingService} from '../../logging.service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { createPopper } from '@popperjs/core';

@Component({
  selector: 'app-add-mindmap',
  imports: [
    NgIf,
    NgForOf,
    ReactiveFormsModule
  ],
  templateUrl: './add-mindmap.component.html',
  standalone: true,
  styleUrls: ['./add-mindmap.component.scss', '../../forms.scss']
})
export class AddMindmapComponent implements OnInit{
  log = new LoggingService("AddMindmapComponent", "mindmap-service")
  service = inject(ModuleApiService)
  mindmapService = inject(MindmapApiService)
  expanded: boolean = false;

  modulSelectData : any;
  mindmapForm: FormGroup = new FormGroup({
    title: new FormControl("", Validators.required),
    modulId: new FormControl(""),
    modulName: new FormControl("")
  });


  expandForm() {
    this.expanded = !this.expanded
    this.mindmapForm.reset()
  }

  ngOnInit(): void {
    this.service.getModulSelectData().subscribe({
      next: data => {
        this.modulSelectData = data
        this.log.debug("got data:")
        console.log(data)
      },
      error: err => {
        this.log.error("Error fetching data. Reason:")
        console.log(err)
      }
    })
  }

  sendNewMindmapRequest() {
    if(this.mindmapForm.valid) {
      this.log.debug("form data VALID")
      this.mindmapService.postNewMindmap(this.mindmapForm.value).subscribe({
        next: () => {
          this.log.debug("new mindmap request sent")
        },
        error: err => {
          console.log(err)
        }
      })

    } else {
      this.mindmapForm.markAllAsTouched()
      this.log.debug("form data INVALID")
    }
  }

  setFormData(modulId: any, modulName: any) {
    this.mindmapForm.patchValue({
      modulId: modulId,
      modulName: modulName
    })
  }
}
