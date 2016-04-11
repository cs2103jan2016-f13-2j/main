package main.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import main.resources.Feedback;
//@@author A0133926A

public class ParserTest {

	
	@Test
	public void testDeleteType() {
		String test1 = "d1";
		String test2 = "e2";
		String test3 = "f3";
		assertTrue(Parser.deleteType(test1)==4);
		assertTrue(Parser.deleteType(test2)==1);
		assertTrue(Parser.deleteType(test3)==2);
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
		String command = "add eating by 20th-Aug;6pm at soc -p 1";
		ArrayList<String> info = new ArrayList<String>();
		info.add("add");
		info.add("eating");
		info.add("by");
		info.add("20th-Aug;6pm");
		info.add("at");
		info.add("soc");
		info.add("-p");
		info.add("1");
		assertEquals(info,Parser.retrieveCommand(command));
	}


	@Test
	public void testIdentifyTaskType(){
		String command1 = "add eating by 20th-Aug;6pm at soc -p 1";
		ArrayList<String> info1 = Parser.retrieveCommand(command1);
		assertEquals(Parser.identifyTaskType(info1),"deadline");

		String command2 = "add eating from 20th-Aug;6pm to 21-aug;6pm at soc -p 1";
		ArrayList<String> info2 = Parser.retrieveCommand(command2);
		assertEquals(Parser.identifyTaskType(info2),"event");

		String command3 = "add eating at soc -p 1";
		ArrayList<String> info3 = Parser.retrieveCommand(command3);
		assertEquals(Parser.identifyTaskType(info3),"floating");

		String command4 = "recur daily eating by 20th-Aug;6pm at soc -p 1 for 3 times";
		ArrayList<String> info4 = Parser.retrieveCommand(command4);
		assertEquals(Parser.identifyTaskType(info4),"recurring");
	}

	@Test
	public void testGetNumber(){
		String command1 = "-de";
		String command2 = "-sd";
		String command3 = "-st";
		String command4 = "-ed";
		String command5 = "-et";
		String command6 = "-l";
		String command7 = "-p";
		String command8 = "-c";
		assertTrue(Parser.getNumber(command1)==1);
		assertTrue(Parser.getNumber(command2)==2);
		assertTrue(Parser.getNumber(command3)==3);
		assertTrue(Parser.getNumber(command4)==4);
		assertTrue(Parser.getNumber(command5)==5);
		assertTrue(Parser.getNumber(command6)==6);
		assertTrue(Parser.getNumber(command7)==7);
		assertTrue(Parser.getNumber(command8)==8);
	}


	@Test
	public void testFindNextCommand(){
		String command = "edit f1 -sd 20th-Aug -st 6pm -l soc -p 1";
		ArrayList<String> info = Parser.retrieveCommand(command);
		assertEquals(Parser.findNextCommand(info,0),2);
		assertEquals(Parser.findNextCommand(info,2),4);
		assertEquals(Parser.findNextCommand(info,4),6);
	}

	@Test
	public void testFormatInputForValidParsing(){
		String command1 = "edit    f1 -sd 20th-Aug    -st 6pm -l   soc -p 1";
		String command2 = "edit f1 -sd 20th-Aug -st 6pm -l soc -p 1";
		assertEquals(Parser.formatInputForValidParsing(command1),command2);
	}

	@Test
	public void testOnlyOneWord(){
		assertTrue(Parser.onlyOneWord("sjsh"));
		assertFalse(Parser.onlyOneWord("sj  sh"));
	}
}
