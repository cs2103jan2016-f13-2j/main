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
		//update task commands
		assertTrue(Shortcuts.shortcuts("-n").equals("-n"));
		assertFalse(Shortcuts.shortcuts("-nn").equals("-n"));
		
		assertTrue(Shortcuts.shortcuts("-sd").equals("-sd"));
		assertFalse(Shortcuts.shortcuts("sd").equals("-sd"));
		
		assertTrue(Shortcuts.shortcuts("-ed").equals("-ed"));
		assertFalse(Shortcuts.shortcuts("-e").equals("-ed"));
		
		assertTrue(Shortcuts.shortcuts("-st").equals("-st"));
		assertFalse(Shortcuts.shortcuts("-stt").equals("-st"));
		
		assertTrue(Shortcuts.shortcuts("-et").equals("-et"));
		assertFalse(Shortcuts.shortcuts("etttt").equals("-et"));
		//display task commands
		assertTrue(Shortcuts.shortcuts("display").equals("display"));
		assertTrue(Shortcuts.shortcuts("view").equals("display"));
		assertFalse(Shortcuts.shortcuts("vvv").equals("display"));
		//search command
		assertTrue(Shortcuts.shortcuts("search").equals("search"));
		assertTrue(Shortcuts.shortcuts("find").equals("search"));
		assertTrue(Shortcuts.shortcuts("s").equals("search"));
		assertTrue(Shortcuts.shortcuts("f").equals("search"));
		//recurring task
		assertTrue(Shortcuts.shortcuts("recurring").equals("recurring"));
		assertTrue(Shortcuts.shortcuts("recur").equals("recurring"));
		assertTrue(Shortcuts.shortcuts("r").equals("recurring"));

		assertTrue(Shortcuts.shortcuts("set").equals("set"));
		
		//invalid cases
		assertTrue(Shortcuts.shortcuts("hahaha").equals("invalid command"));
		assertFalse(Shortcuts.shortcuts("abcdefg add adwd").equals("add"));
	}
}