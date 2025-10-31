package com.studyhub.track.domain.model.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.UUID;

/**
 * A Session consists of blocks which hold the information about how long the user wants to learn certain modules which
 * they created.
 * <ul>
 *     <li>{@code fachId} - Unique ID of the Session</li>
 *     <li>{@code username} – Username of the user</li>
 *     <li>{@code titel} – Title of the Session</li>
 *     <li>{@code beschreibung} – Description of the Session</li>
 *     <li>{@code blocks}</li> - List of blocks which hold the learning time information
 *
 * </ul>
 */
@Data
@AllArgsConstructor
public class Session {
	private UUID fachId;
	private String username;
	private String titel;
	private String beschreibung;
	private List<Block> blocks;

	/**
	 * Returns the total sum of all time fields of each Block of the Session.
	 */
	public Integer getTotalZeit() {
		return blocks.stream()
				.mapToInt(block -> block.getLernzeitSeconds() + block.getPausezeitSeconds())
				.sum();
	}
}
