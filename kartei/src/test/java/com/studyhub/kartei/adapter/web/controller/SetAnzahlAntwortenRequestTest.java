package com.studyhub.kartei.adapter.web.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SetAnzahlAntwortenRequestTest {

	@Test
	public void testConstructorAndGetters() {
		String karteiSetId = "test123";
		Integer anzahlAntworten = 10;

		SetAnzahlAntwortenRequest request = new SetAnzahlAntwortenRequest(karteiSetId, anzahlAntworten);

		assertEquals(karteiSetId, request.karteiSetId());
		assertEquals(anzahlAntworten, request.anzahlAntworten());
	}

	@Test
	public void testEqualsAndHashCode() {
		SetAnzahlAntwortenRequest request1 = new SetAnzahlAntwortenRequest("test123", 10);
		SetAnzahlAntwortenRequest request2 = new SetAnzahlAntwortenRequest("test123", 10);
		SetAnzahlAntwortenRequest request3 = new SetAnzahlAntwortenRequest("otherId", 5);

		assertEquals(request1, request2);
		assertNotEquals(request1, request3);
		assertEquals(request1.hashCode(), request2.hashCode());
		assertNotEquals(request1.hashCode(), request3.hashCode());
	}

	@Test
	public void testToString() {
		SetAnzahlAntwortenRequest request = new SetAnzahlAntwortenRequest("test123", 10);

		String toStringResult = request.toString();

		assertEquals(toStringResult, "SetAnzahlAntwortenRequest[karteiSetId=test123, anzahlAntworten=10]");
	}
}
