import {Component, inject, OnInit} from '@angular/core';
import {MindmapApiService} from './mindmap-api.service';
import {MindmapNode} from './MindmapNode';
import {LoggingService} from '../logging.service';

@Component({
  standalone: true,
  selector: 'app-mindmap-service',
  imports: [],
  templateUrl: './mindmap-service.component.html',
  styleUrls: ['./mindmap-service.component.scss', '../general.scss']
})
export class MindmapServiceComponent implements OnInit{
  log = new LoggingService("MindmapServiceComponent", "mindmap-service");

  service = inject(MindmapApiService);
  mindmaps : MindmapNode[] = []

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
  }

}
