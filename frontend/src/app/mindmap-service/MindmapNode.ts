export interface MindmapNode {
  nodeId : string;
  modulId : string;
  title : string;
  text : string;
  pnodeType : NodeType;
  nodeRole : NodeRole;
  username : string;
  childNodes : MindmapNode[];
}

export enum NodeType {
  SUBJECT = 'SUBJECT',
  PROCESS = 'PROCESS',
}

export enum NodeRole {
  ROOT = 'ROOT',
  CHILD = 'CHILD',
}
