import {Component, ElementRef, inject, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {MindmapNode} from '../MindmapNode';
import {MindmapApiService} from '../mindmap-api.service';
import {LoggingService} from '../../logging.service';
import {NodeFactoryService} from './node-factory.service';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {NodeCreationSignalService} from './node-creation-signal.service';

declare var LeaderLine: any;

@Component({
  selector: 'app-mindmap-details',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './mindmap-details.component.html',
  standalone: true,
  styleUrls: ['./mindmap-details.component.scss', '../../general.scss', 'node.scss'],
  encapsulation: ViewEncapsulation.None // Damit node.scss für dynamisch erstellte DOM-Elemente greift
})
export class MindmapDetailsComponent implements OnInit{
  log = new LoggingService("MindmapDetailsComponent", "mindmap-service")
  route = inject(ActivatedRoute)
  mindmapApiService = inject(MindmapApiService)
  nodeFactory = inject(NodeFactoryService)
  nodeCreationSignalService = inject(NodeCreationSignalService)
  bearbeitenModus = false

  newNodeForm = new FormGroup({
    parentNodeId: new FormControl("", Validators.required),
    title: new FormControl("", Validators.required),
    nodeType: new FormControl("", Validators.required)
  })

  mindmap : MindmapNode | any
  edges : any[] = []

  ngOnInit(): void {
    this.loadMindmap()
  }

  loadMindmap() {
    this.route.paramMap.subscribe(params => {
      let modulId = params.get('modulId')!;
      this.mindmapApiService.getMindmapByModulId(modulId).subscribe({
        next: data => {
          this.mindmap = data;
          this.renderNodes()
          this.dragNode()
        },
        error: err => {
          console.log(err)
        }
      })
    });
  }

  renderNodes() {
    //Erst Elemente erstellen
    this.traverseMindmap(
      this.mindmap,
      (node, x, y) => this.nodeFactory.createNodeDivElement(node, x, y),
      () => {}
    );
    //dann Kanten ziehen, sonst bug
    this.traverseMindmap(
      this.mindmap,
      () => {},
      (parent, child) => this.drawDirectedEdge(parent, child)
    );
  }

  //TODO: Koordinaten werden hier berechnet, es sollen später die (x,y)-Tupel für jede node gespeichert werden, damit User rearrangen kann
  traverseMindmap = (
    node: MindmapNode,
    createNodeDivElement: (node: MindmapNode, x: number, y: number) => void,
    drawDirectedEdge: (parent: MindmapNode, child: MindmapNode) => void,
    x: number = 100,
    y: number = 200
  ): void => {
    createNodeDivElement(node, x, y);

    const verticalSpacing = 120;
    const horizontalSpacing = 200;

    let offsetY = y;

    //Traverse each child recursively
    for (const child of node.childNodes) {
      drawDirectedEdge(node, child);
      const childX = x + horizontalSpacing;
      const childY = offsetY;

      this.traverseMindmap(child, createNodeDivElement, drawDirectedEdge, childX, childY);

      offsetY += verticalSpacing;
    }
  };

  drawDirectedEdge(v : MindmapNode, w : MindmapNode)  {
    let from = document.getElementById(v.nodeId)
    let to = document.getElementById(w.nodeId)

    let edge = new LeaderLine(from, to)
    this.edges.push(edge)
  }

  switchBearbeitenModus() {
    this.bearbeitenModus = !this.bearbeitenModus;
    const btns = document.getElementsByClassName("addNodeBtn");
    const show = this.bearbeitenModus;

    for (let i = 0; i < btns.length; i++) {
      btns[i].classList.toggle("hide-btn", !show);
      btns[i].classList.toggle("display-btn", show);
    }
  }

  sendNewNodeData() {
    const parentId = this.nodeCreationSignalService.parentIdSignal();
    this.newNodeForm.patchValue({"parentNodeId": parentId});

    if(this.newNodeForm.valid) {
      this.log.debug("form data VALID")
      console.log(this.newNodeForm.value)
      const data = this.newNodeForm.value

      this.mindmapApiService.postNewNode(data).subscribe({
        next: () => {
          this.log.debug("New node created successfully");
          this.newNodeForm.reset();
          this.nodeCreationSignalService.setParentId('');
          this.refreshNodes()
        },
        error: err => {
          this.log.error("Error creating new node:");
          console.log(err)
        }
      })
    } else {
      this.log.debug("form data INVALID")
      this.newNodeForm.markAllAsTouched();
    }
  }

  refreshNodes() {
    const mindmapDiv = document.getElementById("mindmap-container")
    while (mindmapDiv?.firstChild) {
      mindmapDiv.removeChild(mindmapDiv.firstChild);
    }
    this.loadMindmap()
    this.renderNodes()
  }

  dragNode() {
    const draggableDivs = document.querySelectorAll<HTMLElement>('.dragable');

    let isDragging = false;
    let currentDiv: HTMLElement | null = null;
    let offsetX = 0;
    let offsetY = 0;

    draggableDivs.forEach(div => {
      div.addEventListener('mousedown', (e) => {
        isDragging = true;
        currentDiv = div;
        offsetX = e.clientX - div.offsetLeft;
        offsetY = e.clientY - div.offsetTop;
      });
    });

    document.addEventListener('mousemove', (e) => {
      if (!isDragging || !currentDiv) return;

      currentDiv.style.left = `${e.clientX - offsetX}px`;
      currentDiv.style.top = `${e.clientY - offsetY}px`;

      for( let edge of this.edges) {
        edge.position()
      }
    });

    document.addEventListener('mouseup', () => {
      isDragging = false;
      currentDiv = null;
    });

  }
}
