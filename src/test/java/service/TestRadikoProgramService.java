package service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ctp.service.RadikoProgramService;

public class TestRadikoProgramService {
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCalc() {
		assertEquals(100, RadikoProgramService.calc());
	}

}
