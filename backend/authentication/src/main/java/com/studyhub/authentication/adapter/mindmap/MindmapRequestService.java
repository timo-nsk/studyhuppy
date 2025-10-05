package com.studyhub.authentication.adapter.mindmap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MindmapRequestService {
    @Value("${mindmap.api-url}")
    private String mindmapApiUrl;

    public boolean sendDeleteAllRequest(String username) {
        //TODO IMPLEMENT: when users deletes account, all data should be deleted
        return true;
    }
}
