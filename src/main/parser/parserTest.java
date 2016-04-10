package main.parser;

import static org.junit.Assert.*;
import org.junit.Test;
//@@author A0133926A

public class parserTest {
	String test1 = "d1";
	String test2 = "e2";
	String test3 = "f3";
	
	@Test
	public void testDeleteType() {
		assertTrue(Parser.deleteType(test1)==1);
		assertTrue(Parser.deleteType(test2)==2);
		assertTrue(Parser.deleteType(test3)==3);
	}
	
	@Test
	public void testDeleteNumber() {
		assertTrue(Parser.deleteNumber(test1)==1);
		assertTrue(Parser.deleteNumber(test2)==2);
		assertTrue(Parser.deleteNumber(test3)==3);
	}
}
