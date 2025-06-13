import {inject, Injectable} from '@angular/core';
import {MindmapNode} from '../MindmapNode';
import {LoggingService} from '../../logging.service';
import {NodeCreationSignalService} from './node-creation-signal.service';

@Injectable({
  providedIn: "root"
})
export class NodeFactoryService {
  log = new LoggingService("NodeFactoryService", "mindmap-service")
  nodeCreationSignalService = inject(NodeCreationSignalService)

  createNodeDivElement(node : MindmapNode, x : number, y : number) {
    /** STRUCTURE
     * <div class="node-wrapper">
     *   <div class="subject-node">
     *   <div>
     *     <a>
     *       <svg>
     */
      //Prepare node wrapper div
    const nodeWrapper = document.createElement("div")
    nodeWrapper.classList.add("node-wrapper")
    nodeWrapper.classList.add("dragable")
    nodeWrapper.classList.add("shd")
    nodeWrapper.id = node.nodeId;
    nodeWrapper.style.left = x + "px";
    nodeWrapper.style.top = y + "px";

    //Prepare actual nodeDiv
    const newNodeDiv = document.createElement("div");
    newNodeDiv.classList.add("subject-node")
    newNodeDiv.classList.add("node")
    const p = document.createElement("p");
    const span = document.createElement("span");
    span.textContent = node.title;


    const addBtnDiv = this.createAddChildButton(node.nodeId)


    p.appendChild(span);
    newNodeDiv.appendChild(p);
    nodeWrapper.appendChild(addBtnDiv)
    nodeWrapper.appendChild(newNodeDiv)


    const mindmapContainer = document.getElementById("mindmap-container");
    mindmapContainer!.appendChild(nodeWrapper);
  }

  createAddChildButton(nodeId : string) {
    /** STRUCTURE:
     *   <a>
     *     <svg></svg>
     *   </a>
     */
    const svgNS = "http://www.w3.org/2000/svg";

    const div = document.createElement("div")
    div.classList.add("addNodeBtn")
    div.classList.add("hide-btn")

    // SVG-Element erstellen
    const svg = document.createElementNS(svgNS, "svg");
    svg.classList.add("add-icon")
    svg.setAttribute("xmlns", svgNS);
    svg.setAttribute("height", "20px");
    svg.setAttribute("width", "20px");
    svg.setAttribute("viewBox", "0 -960 960 960");
    svg.setAttribute("fill", "#5985E1");

    // Path-Element hinzufügen
    const path = document.createElementNS(svgNS, "path");
    path.setAttribute("d", "M444-288h72v-156h156v-72H516v-156h-72v156H288v72h156v156Zm36.28 192Q401-96 331-126t-122.5-82.5Q156-261 126-330.96t-30-149.5Q96-560 126-629.5q30-69.5 82.5-122T330.96-834q69.96-30 149.5-30t149.04 30q69.5 30 122 82.5T834-629.28q30 69.73 30 149Q864-401 834-331t-82.5 122.5Q699-156 629.28-126q-69.73 30-149 30Zm-.28-72q130 0 221-91t91-221q0-130-91-221t-221-91q-130 0-221 91t-91 221q0 130 91 221t221 91Zm0-312Z");

    // SVG mit path zusammenbauen
    svg.appendChild(path);

    // Link bauen
    const link = document.createElement("a");

    link.addEventListener("click", () => {
      this.nodeCreationSignalService.setParentId(nodeId);
      this.nodeCreationSignalService.setShowNewNodeForm()
    });


    link.appendChild(svg)
    div.appendChild(link)
    return div
  }
}
