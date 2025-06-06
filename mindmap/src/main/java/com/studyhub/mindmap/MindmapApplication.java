package com.studyhub.mindmap;

import com.studyhub.mindmap.application.service.MindmapRepository;
import com.studyhub.mindmap.domain.model.ChildMindmapNode;
import com.studyhub.mindmap.domain.model.MindmapNode;
import com.studyhub.mindmap.domain.model.NodeType;
import com.studyhub.mindmap.domain.model.RootMindmapNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MindmapApplication{
	public static void main(String[] args) {
		SpringApplication.run(MindmapApplication.class, args);
	}
}