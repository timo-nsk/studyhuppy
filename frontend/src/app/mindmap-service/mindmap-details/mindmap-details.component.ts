import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MindmapNode} from '../MindmapNode';
import {MindmapApiService} from '../mindmap-api.service';

@Component({
  selector: 'app-mindmap-details',
  imports: [],
  templateUrl: './mindmap-details.component.html',
  styleUrls: ['./mindmap-details.component.scss', '../../general.scss']
})
export class MindmapDetailsComponent implements OnInit{
  route = inject(ActivatedRoute)
  service = inject(MindmapApiService)

  mindmap : MindmapNode | any;

  ngOnInit(): void {
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

}
