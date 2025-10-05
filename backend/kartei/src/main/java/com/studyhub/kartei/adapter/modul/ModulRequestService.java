package com.studyhub.kartei.adapter.modul;

import com.studyhub.kartei.adapter.aspects.LogExecutionTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ModulRequestService {

	private Logger log = LoggerFactory.getLogger(ModulRequestService.class);

	@LogExecutionTime
	public Map<UUID, String> getModulApiMap(){
		String uri = "http://localhost:8080/api/module-map";

		CompletableFuture<Map> futureMap = WebClient.create()
				.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(Map.class)
				.timeout(Duration.of(3, ChronoUnit.SECONDS))
				.toFuture();

		try {
			Map result = futureMap.get();
			log.info("request to %s".formatted(uri));
			return (Map<UUID, String>) result;
		} catch (InterruptedException | ExecutionException | CancellationException e) {
			log.error("FAILED request to %s".formatted(uri));
			throw new ModulRequestException(e.getMessage());
		}
	}

	@LogExecutionTime
	public String getModulName(UUID modulFachId) throws ExecutionException, InterruptedException {
		String uri = "http://localhost:8080/api/module-name?modulFachId=%s".formatted(modulFachId.toString());

		CompletableFuture<String> futureModulName = WebClient.create()
				.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(String.class)
				.timeout(Duration.of(3, ChronoUnit.SECONDS))
				.toFuture();

		try {
			log.info("request to %s".formatted(uri));
			return futureModulName.get();
		} catch (InterruptedException | ExecutionException | CancellationException e) {
			log.error("FAILED request to %s".formatted(uri));
			throw new ModulRequestException(e.getMessage());
		}
	}
}