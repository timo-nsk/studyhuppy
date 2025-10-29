package com.studyhub.track.domain.model.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

/**
 * A Block is part of a Session and holds all information needed for learning a module.
 *
 * <ul>
 *     <li>{@code fachId} - Unique ID of the Block</li>
 *     <li>{@code modulId} - ID of the module that is being learned</li>
 *     <li>{@code modulName} - Name of the module</li>
 *     <li>{@code lernzeitSeconds} - The duration the module is studied in seconds</li>
 *     <li>{@code pausezeitSeconds} - The pause duration between learning periods</li>
 * </ul>
 */
@Data
@AllArgsConstructor
public class Block {
	private UUID fachId;
	private UUID modulId;
	private String modulName;
	private int lernzeitSeconds;
	private int pausezeitSeconds;
}
