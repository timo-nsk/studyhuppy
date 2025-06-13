import {Component, inject, OnInit} from '@angular/core';
import {MindmapApiService} from './mindmap-api.service';
import {MindmapNode} from './MindmapNode';
import {LoggingService} from '../logging.service';
import {KeyValuePipe, NgForOf, NgIf} from '@angular/common';
import {RouterLink, RouterOutlet} from '@angular/router';
import {AddMindmapComponent} from './add-mindmap/add-mindmap.component';

@Component({
  standalone: true,
  selector: 'app-mindmap-service',
  imports: [
    NgIf,
    RouterLink,
    NgForOf,
    AddMindmapComponent,
    RouterOutlet,
    KeyValuePipe
  ],
  templateUrl: './mindmap-service.component.html',
  styleUrls: ['./mindmap-service.component.scss', '../general.scss']
})
export class MindmapServiceComponent implements OnInit{
  log = new LoggingService("MindmapServiceComponent", "mindmap-service");

  service = inject(MindmapApiService);
  moduleMindmaps: { [key: string]: MindmapNode[] } = {}
  otherMindmaps: any;


  ngOnInit(): void {
    this.service.getOtherMindmapsByUsername().subscribe({
      next: (data) => {
        this.otherMindmaps = data;
        this.log.debug("Got Others data:")
        console.log(this.otherMindmaps)
      },
      error: (err) => {
        this.log.error(`Error while getting mindmaps: Reason: ${err}`);
      }
    });
    this.service.getMindmapsGroupedByModule().subscribe({
      next: (data : { [key: string]: MindmapNode[] }
      ) => {
        this.moduleMindmaps = data;
        this.log.debug("Got MODUL data:")
        console.log(this.moduleMindmaps)
      },
      error: (err) => {
        this.log.error(`Error while getting mindmaps: Reason: ${err}`);
      }
    });
  }

  emptyMindmaps(): boolean {
    if(this.moduleMindmaps && this.otherMindmaps) {
      return Object.keys(this.moduleMindmaps).length === 0 && this.otherMindmaps.length === 0
    }
    return true

  }

  emptyModulMindmaps() {
    console.log("module length: " + Object.keys(this.moduleMindmaps).length)
    return Object.keys(this.moduleMindmaps).length === 0
  }

  emptyOtherMindmaps() {
    if (this.otherMindmaps){
      console.log("others length: " + this.otherMindmaps.length)
      return this.otherMindmaps.length === 0
    }
    return true
  }

  forceReload($event: boolean) {
      if($event) {
        this.ngOnInit()
      }
  }
}
