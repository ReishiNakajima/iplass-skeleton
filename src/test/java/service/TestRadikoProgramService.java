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
	public void testCalc_1() {
		assertEquals(100, RadikoProgramService.calc());
	}
	
	@Test
	public void testCalc_2() {
		assertEquals(100, RadikoProgramService.calc());
	}

}
