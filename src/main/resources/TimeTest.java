package main.resources;
//@@author A0124711U
import static org.junit.Assert.*;

import org.junit.Test;

public class TimeTest {

	/**
	 * Tests the constructor for Time.
	 */
	@Test
	public void test1() {
		Time time = new Time();
		assertEquals(-1, time.getHour());
		assertEquals(-1, time.getMinute());
		
		time = new Time(12, 34);
		assertEquals(12, time.getHour());
		assertEquals(34, time.getMinute());
		
		time = new Time(5, 6);
		assertEquals(5, time.getHour());
		assertEquals(6, time.getMinute());
	}
	
	/**
	 * Tests the validity checks for Time.
	 */
	@Test
	public void test2() {
		Time time = new Time();
		assertFalse(time.isValid());
		
		time = new Time(-1, 12);
		assertFalse(time.isValid());
		
		time = new Time(12, 60);
		assertFalse(time.isValid());
		
		time = new Time(24, 57);
		assertFalse(time.isValid());
		
		time = new Time(6, -1);
		assertFalse(time.isValid());
		
		time = new Time(-1, 59);
		assertFalse(time.isValid());
		
		time = new Time(12, 34);
		assertTrue(time.isValid());
		
		time = new Time(23, 59);
		assertTrue(time.isValid());
		
		time = new Time(0, 0);
		assertTrue(time.isValid());
	}

}
