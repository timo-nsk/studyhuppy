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
  mindmaps : MindmapNode[] = []
  module: { [key: string]: MindmapNode[] } = {}


  ngOnInit(): void {
    this.service.getAllMindmapsByUsername().subscribe({
      next: (data) => {
        this.mindmaps = data;
        this.log.debug("Got data:")
        console.log(this.mindmaps)
      },
      error: (err) => {
        this.log.error(`Error while getting mindmaps: Reason: ${err}`);
      }
    });
    this.service.getMindmapsGroupedByModule().subscribe({
      next: (data : { [key: string]: MindmapNode[] }
      ) => {
        this.module = data;
        this.log.debug("Got data:")
        console.log(this.module)
      },
      error: (err) => {
        this.log.error(`Error while getting mindmaps: Reason: ${err}`);
      }
    });
  }

  emptyMindmaps(): boolean {
    return this.mindmaps.length === 0
  }
}
