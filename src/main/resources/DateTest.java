package main.resources;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import main.resources.Feedback;

//@@author A0133926A
public class DateTest {
	@Test
	public void testGetDay(){
		Date date = new Date(5,8,2016);
		assertTrue(date.getDay()==5);
		assertFalse(date.getDay()==6);
	}
	
	
	@Test
	public void testGetMonth(){
		Date date = new Date(5,8,2016);
		assertTrue(date.getMonth()==8);
		assertFalse(date.getMonth()==6);
	}
	
	@Test
	public void testGetYear(){
		Date date = new Date(5,8,2016);
		assertTrue(date.getYear()==2016);
		assertFalse(date.getYear()==16);		
	}
	
	@Test
	public void testDateString(){
		Date date = new Date(5,8,2016);
		assertTrue(date.getDateString().equals("5-8-2016"));
		assertFalse(date.getDateString().equals("ha"));		
	}

	
	
	@Test
	public void testEquals(){
		Date date1 = new Date(5,8,2016);
		Date date2 = new Date(5,8,2016);
		Date date3 = new Date(5,3,2016);
		
		assertTrue(date1.equals(date2));
		assertFalse(date1.equals(date3));
	}
	
	
	public void testIsLeapYear(){
		Date date1 = new Date(5,8,2016);
		Date date2 = new Date(5,8,2017);
		Date date3 = new Date(5,8,2018);
		assertTrue(date1.isLeapYear());
		assertTrue(date2.isLeapYear());
		assertTrue(date3.isLeapYear());
		
		
		
		
	}
}
