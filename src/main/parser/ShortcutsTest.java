package main.parser;

import static org.junit.Assert.*;

import org.junit.Test;
//@@author A0133926A
public class ShortcutsTest {
	@Test
	public void testShortcuts() {
	
		//flexi commands for adding a task
		assertTrue(Shortcuts.shortcuts("add").equals("add"));
		assertTrue(Shortcuts.shortcuts("a").equals("add"));
		assertTrue(Shortcuts.shortcuts("create").equals("add"));
		assertTrue(Shortcuts.shortcuts("c").equals("add"));
		assertFalse(Shortcuts.shortcuts("addddddd").equals("add"));
		
		//delete task commands
		assertTrue(Shortcuts.shortcuts("delete").equals("delete"));
		assertTrue(Shortcuts.shortcuts("del").equals("delete"));
		assertTrue(Shortcuts.shortcuts("d").equals("delete"));
		assertFalse(Shortcuts.shortcuts("ddddddd").equals("delete"));

		//search command
		assertTrue(Shortcuts.shortcuts("search").equals("search"));
		assertTrue(Shortcuts.shortcuts("find").equals("search"));
		assertTrue(Shortcuts.shortcuts("s").equals("search"));
		assertTrue(Shortcuts.shortcuts("f").equals("search"));
		
		//recurring task
		assertTrue(Shortcuts.shortcuts("recurring").equals("recurring"));
		assertTrue(Shortcuts.shortcuts("recur").equals("recurring"));
		assertTrue(Shortcuts.shortcuts("r").equals("recurring"));
		
		//redo and undo
		assertTrue(Shortcuts.shortcuts("redo").equals("redo"));
		assertTrue(Shortcuts.shortcuts("undo").equals("undo"));
		
		//set
		assertTrue(Shortcuts.shortcuts("set").equals("set"));
		
		//complete command
		assertTrue(Shortcuts.shortcuts("complete").equals("complete"));
		assertTrue(Shortcuts.shortcuts("mark").equals("complete"));
		assertTrue(Shortcuts.shortcuts("done").equals("complete"));
		assertTrue(Shortcuts.shortcuts("finish").equals("complete"));
		assertTrue(Shortcuts.shortcuts("finished").equals("complete"));
		
		//incomplete command
		assertTrue(Shortcuts.shortcuts("incomplete").equals("uncomplete"));
		assertTrue(Shortcuts.shortcuts("uncomplete").equals("uncomplete"));

		//invalid cases
		assertFalse(Shortcuts.shortcuts("hahaha").equals("invalid command"));
		assertFalse(Shortcuts.shortcuts("abcdefg add adwd").equals("add"));
		
	}
	
	@Test
	public void testDiffDateFormat(){
		//test tomorrow shortcut
		assertTrue(Shortcuts.diffDateFormat("today").equals("today"));
		//test tomorrow shortcut
		assertTrue(Shortcuts.diffDateFormat("tmr").equals("tomorrow"));
		assertTrue(Shortcuts.diffDateFormat("tmrw").equals("tomorrow"));
		assertTrue(Shortcuts.diffDateFormat("tomorrow").equals("tomorrow"));
		
		//test Monday shortcut
		assertTrue(Shortcuts.diffDateFormat("mon").equals("monday"));
		assertTrue(Shortcuts.diffDateFormat("monday").equals("monday"));
		
		//test Tuesday shortcut
		assertTrue(Shortcuts.diffDateFormat("tues").equals("tuesday"));
		assertTrue(Shortcuts.diffDateFormat("tuesday").equals("tuesday"));
		
		//test Wednesday shortcut
		assertTrue(Shortcuts.diffDateFormat("wed").equals("wednesday"));
		assertTrue(Shortcuts.diffDateFormat("wednesday").equals("wednesday"));
				
		//test Thursday shortcut
		assertTrue(Shortcuts.diffDateFormat("thur").equals("thursday"));
		assertTrue(Shortcuts.diffDateFormat("thurs").equals("thursday"));
		assertTrue(Shortcuts.diffDateFormat("thursday").equals("thursday"));	
		
		//test Friday shortcut
		assertTrue(Shortcuts.diffDateFormat("fri").equals("friday"));
		assertTrue(Shortcuts.diffDateFormat("friday").equals("friday"));
		
		//test Saturday shrotcut
		assertTrue(Shortcuts.diffDateFormat("sat").equals("saturday"));
		assertTrue(Shortcuts.diffDateFormat("saturday").equals("saturday"));
		
		//test Sunday shortcut
		assertTrue(Shortcuts.diffDateFormat("sun").equals("sunday"));
		assertTrue(Shortcuts.diffDateFormat("sunday").equals("sunday"));
		
		//test January shortcut
		assertTrue(Shortcuts.diffDateFormat("jan").equals("January"));
		assertTrue(Shortcuts.diffDateFormat("january").equals("January"));
		
		//test February shortcut
		assertTrue(Shortcuts.diffDateFormat("feb").equals("February"));
		assertTrue(Shortcuts.diffDateFormat("february").equals("February"));
		
		//test March shortcut
		assertTrue(Shortcuts.diffDateFormat("mar").equals("March"));
		assertTrue(Shortcuts.diffDateFormat("march").equals("March"));
		
		//test April shortcut
		assertTrue(Shortcuts.diffDateFormat("apr").equals("April"));
		assertTrue(Shortcuts.diffDateFormat("april").equals("April"));
		
		//test May shortcut
		assertTrue(Shortcuts.diffDateFormat("may").equals("May"));
		
		//test June shortcut
		assertTrue(Shortcuts.diffDateFormat("jun").equals("June"));
		assertTrue(Shortcuts.diffDateFormat("june").equals("June"));
		
		//test July shortcut
		assertTrue(Shortcuts.diffDateFormat("jul").equals("July"));
		assertTrue(Shortcuts.diffDateFormat("july").equals("July"));
		
		//test August shortcut
		assertTrue(Shortcuts.diffDateFormat("aug").equals("August"));
		assertTrue(Shortcuts.diffDateFormat("august").equals("August"));
		
		//test September shortcut
		assertTrue(Shortcuts.diffDateFormat("sep").equals("September"));
		assertTrue(Shortcuts.diffDateFormat("september").equals("September"));
		
		//test October shortcut
		assertTrue(Shortcuts.diffDateFormat("oct").equals("October"));
		assertTrue(Shortcuts.diffDateFormat("october").equals("October"));
		
		//test November shortcut
		assertTrue(Shortcuts.diffDateFormat("nov").equals("November"));
		assertTrue(Shortcuts.diffDateFormat("november").equals("November"));
		
		//test December shortcut
		assertTrue(Shortcuts.diffDateFormat("dec").equals("December"));
		assertTrue(Shortcuts.diffDateFormat("december").equals("December"));
		
		//invalid cases
		assertFalse(Shortcuts.diffDateFormat("hah").equals("December"));
		assertFalse(Shortcuts.diffDateFormat("decembers").equals("December"));
	}
}