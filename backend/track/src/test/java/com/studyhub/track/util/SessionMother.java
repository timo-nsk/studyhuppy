package com.studyhub.track.util;

import com.studyhub.track.domain.model.session.Block;
import com.studyhub.track.domain.model.session.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SessionMother {

	public static Session createSessionWithNRandomBlocks(UUID sessionId, int n) {
		List<Block> blocks = new ArrayList<>();

		for(int i = 1; i < n+1; i++) {
			blocks.add(new Block(UUID.randomUUID(), UUID.randomUUID(), "Modul " + i, 1000*i, 2000*i));
		}

		return new Session(
				sessionId,
				"Test Session",
				"Test Description",
				"Test Location",
				blocks
		);
	}

	public static Session createSessionWithNRandomBlocks(int n) {
		List<Block> blocks = new ArrayList<>();

		for(int i = 1; i < n+1; i++) {
			blocks.add(new Block(UUID.randomUUID(), UUID.randomUUID(), "Modul " + i,1000*i, 2000*i));
		}

		return new Session(
				UUID.randomUUID(),
				"Test Session",
				"Test Description",
				"Test Location",
				blocks
		);
	}

	public static Session createSessionWithTwoBlocks(UUID sessionId) {
		List<Block> blocks = new ArrayList<>();

		blocks.add(new Block(UUID.fromString("11111111-1111-1111-1111-111111111111"), UUID.fromString("11111111-1111-1111-1111-111111111112"), "Modul A", 2000, 2000));
		blocks.add(new Block(UUID.fromString("21111111-1111-1111-1111-111111111111"), UUID.fromString("11111111-1111-1111-1111-111111111112"), "Modul B", 2000, 2000));

		return new Session(
				sessionId,
				"Test Session",
				"Test Description",
				"Test Location",
				blocks
		);
	}
}
