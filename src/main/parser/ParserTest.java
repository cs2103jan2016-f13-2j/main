package main.parser;

import static org.junit.Assert.*;
import org.junit.Test;
//@@author A0133926A

public class ParserTest {

	
	@Test
	public void testDeleteType() {
		String test1 = "d1";
		String test2 = "e2";
		String test3 = "f3";
		assertTrue(Parser.deleteType(test1)==1);
		assertTrue(Parser.deleteType(test2)==2);
		assertTrue(Parser.deleteType(test3)==3);
	}
	
	@Test
	public void testDeleteNumber() {
		String test1 = "d1";
		String test2 = "e2";
		String test3 = "f3";
		assertTrue(Parser.deleteNumber(test1)==1);
		assertTrue(Parser.deleteNumber(test2)==2);
		assertTrue(Parser.deleteNumber(test3)==3);
	}

	@Test
	public void testRetrieveCommand(){

	}

	@Test
	public void testSetUserInputForRedo(){
		
	}

	@Test
	public void testSetUserInputForUndo(){
		
	}

	@Test
	public void testSetUserInputForHome(){
		
	}

	@Test
	public void testSetUserInputForExport(){
		
	}

	@Test
	public void testSetUserInputForImport(){
		
	}

	@Test
	public void testSetUserInputForUncomplete(){
		
	}

	@Test
	public void testSetUserInputForComplete(){
		
	}

	@Test
	public void testSetUserInputForSort(){
		
	}

	@Test
	public void testSetUserInputForSearch(){
		
	}

	@Test
	public void testSetUserInputForEdit(){
		
	}

	@Test
	public void testSetUserInputForDelete(){
		
	}

	@Test
	public void testSetUserInputForAdd(){
		
	}

	@Test
	public void testSetUserInputForRecurring(){
		
	}

	@Test
	public void testIdentifyTaskType(){
		
	}

	@Test
	public void testGetNumber(){
		
	}


	@Test
	public void testFindNextCommand(){
		
	}

	@Test
	public void testFormatInputForValidParsing(){
		
	}

	@Test
	public void testOnlyOneWord(){
		
	}

}
