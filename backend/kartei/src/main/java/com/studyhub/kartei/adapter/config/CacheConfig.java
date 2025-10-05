package com.studyhub.kartei.adapter.config;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableCaching
public class CacheConfig {
	/**
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	StapelService stapelService;

	private final Logger log = LoggerFactory.getLogger(CacheConfig.class);

	@PostConstruct
	public void preloadCache() {
		Cache cache = cacheManager.getCache("stapelListCache");
		List<Stapel> stapelListe = stapelService.findAllStapel();

		for (Stapel s: stapelListe) cache.put(s.getFachId().toString(), s);

		log.info("Initialized cache with all stapel");
	}
	**/
}
