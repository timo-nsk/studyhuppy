import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MindmapNode} from '../MindmapNode';
import {MindmapApiService} from '../mindmap-api.service';

declare var LeaderLine: any;

@Component({
  selector: 'app-mindmap-details',
  imports: [],
  templateUrl: './mindmap-details.component.html',
  styleUrls: ['./mindmap-details.component.scss', '../../general.scss', 'node.scss']
})
export class MindmapDetailsComponent implements OnInit{
  route = inject(ActivatedRoute)
  service = inject(MindmapApiService)

  mindmap : MindmapNode | any;

  ngOnInit(): void {
    this.drawLine()
    this.route.paramMap.subscribe(params => {
      let modulId = params.get('modulId')!;
      this.service.getMindmapByModulId(modulId).subscribe({
        next: data => {
          this.mindmap = data;
          console.log(this.mindmap)
        },
        error: err => {
          console.log(err)
        }
      })
    });
  }

  drawLine()  {
    let node1 = document.getElementById("node-1")
    let node2 = document.getElementById("node-2")

    new LeaderLine(node1, node2)
  }

}
