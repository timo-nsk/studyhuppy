package com.studyhub.mindmap.adapter.modul;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ModulApiAdatper {
    @Value("${adapter.uri.modul}")
    private String modulApiUri;

    public CompletableFuture<List<String>> getModulNames(List<String> modulIds, String token) {
          return WebClient.create()
                  .post()
                  .uri(modulApiUri + "/get-modul-names")
                  .contentType(MediaType.APPLICATION_JSON)
                  .bodyValue(modulIds)
                  .header("Authorization", "Bearer " + token)
                  .retrieve()
                  .bodyToMono(new ParameterizedTypeReference<List<String>>() {})
                  .toFuture();
    }
}
