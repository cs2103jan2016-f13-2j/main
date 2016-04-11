package main.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
//@@author A0133926A

import main.resources.Task;
import main.resources.Time;
import main.parser.Parser;
import main.parser.CreateTask;
//@@author A0133926A

public class CreateTaskTest {
	
	@Test
	public void testCreateDeadline(){
		String taskType = "deadline";
		String command = "add eating by 20th-Aug;6pm at soc -p 1";
		ArrayList<String> info = Parser.retrieveCommand(command);
		Task task = CreateTask.createDeadline(taskType,info);
		assertEquals(task.getTaskName(),"deadline task");
		assertEquals(task.getTaskDetails(),"eating");
		assertEquals(task.getTaskStartTime().getHour(),18);
		assertEquals(task.getTaskStartTime().getMinute(),0);
		assertEquals(task.getTaskStartDate().getDay(),20);
		assertEquals(task.getTaskStartDate().getMonth(),8);
		assertEquals(task.getTaskStartDate().getYear(),2016);
		assertEquals(task.getTaskLocation(),"soc");
		assertEquals(task.getPriority(),1);
	}

	@Test
	public void testCreateEvent(){
		String taskType = "event";
		String command = "add eating from 20th-Aug;6pm to 21st-Aug;6pm at soc -p 1";
		ArrayList<String> info = Parser.retrieveCommand(command);
		Task task = CreateTask.createEvent(taskType,info);
		assertEquals(task.getTaskName(),"event task");
		assertEquals(task.getTaskDetails(),"eating");
		assertEquals(task.getTaskStartTime().getHour(),18);
		assertEquals(task.getTaskStartTime().getMinute(),0);
		assertEquals(task.getTaskStartDate().getDay(),20);
		assertEquals(task.getTaskStartDate().getMonth(),8);
		assertEquals(task.getTaskStartDate().getYear(),2016);
		
		assertEquals(task.getTaskEndTime().getHour(),18);
		assertEquals(task.getTaskEndTime().getMinute(),0);
		assertEquals(task.getTaskEndDate().getDay(),21);
		assertEquals(task.getTaskEndDate().getMonth(),8);
		assertEquals(task.getTaskEndDate().getYear(),2016);
		
		assertEquals(task.getTaskLocation(),"soc");
		assertEquals(task.getPriority(),1);
	}

	@Test
	public void testCreateFloating(){
		String taskType = "floating";
		String command = "add eating at soc -p 1";
		ArrayList<String> info = Parser.retrieveCommand(command);
		Task task = CreateTask.createFloating(taskType,info);
		assertEquals(task.getTaskName(),"floating task");
		assertEquals(task.getTaskDetails(),"eating");
		assertEquals(task.getTaskLocation(),"soc");
		assertEquals(task.getPriority(),1);
	}

	@Test
	public void testCreateRecurring(){
		String taskType = "recurring";
		String command = "recurring daily eating from 20th-Aug;6pm to 21st-Aug;6pm at soc -p 1 for 3 times";

		ArrayList<String> info = Parser.retrieveCommand(command);
		Task task = CreateTask.createRecurring(taskType,info);
		assertEquals(task.getTaskName(),"recurring task");
		assertEquals(task.getTaskDetails(),"eating");
		
		assertEquals(task.getTaskStartTime().getHour(),18);
		assertEquals(task.getTaskStartTime().getMinute(),0);
		assertEquals(task.getTaskStartDate().getDay(),20);
		assertEquals(task.getTaskStartDate().getMonth(),8);
		assertEquals(task.getTaskStartDate().getYear(),2016);
		
		assertEquals(task.getTaskEndTime().getHour(),18);
		assertEquals(task.getTaskEndTime().getMinute(),0);
		assertEquals(task.getTaskEndDate().getDay(),21);
		assertEquals(task.getTaskEndDate().getMonth(),8);
		assertEquals(task.getTaskEndDate().getYear(),2016);
		assertEquals(task.getTaskLocation(),"soc");
		assertEquals(task.getRecurFrequency(),1);
		assertEquals(task.getPriority(),1);
		assertEquals(task.getRecurTime(),3);
	}

	@Test
	public void testGetDetail(){
		String command = "add eating from 20th-Aug;6pm to 21st-Aug;6pm at soc -p 1";
		ArrayList<String> info = Parser.retrieveCommand(command);
		assertEquals(CreateTask.getDetail(info,1,2),"eating");
	}

	@Test
	public void testGetLocation(){
		String command = "add eating from 20th-Aug;6pm to 21st-Aug;6pm at soc -p 1";
		ArrayList<String> info = Parser.retrieveCommand(command);
		assertEquals(CreateTask.getLocation(info,7,8),"soc");
	}

	@Test
	public void testGetTime(){
		String dateAndTime = "20th-Aug;6pm";
		
		assertEquals(CreateTask.getTime(dateAndTime).getHour(),18);
		assertEquals(CreateTask.getTime(dateAndTime).getMinute(),0);
		
	}

	@Test
	public void testGetDate(){
		String dateAndTime = "20th-Aug;6pm";
	
		assertEquals(CreateTask.getDate(dateAndTime).getDay(),20);
		assertEquals(CreateTask.getDate(dateAndTime).getMonth(),8);
		assertEquals(CreateTask.getDate(dateAndTime).getYear(),2016);
		

	}

	@Test
	public void testGetPriority(){
		String priority = "2";
		assertEquals(CreateTask.getPriority(priority),2);
	}

	@Test
	public void testGetFrequency(){
		String weekly = "weekly";
		assertEquals(CreateTask.getFrequency(weekly),2);
	}

	@Test
	public void testCompareMonth(){
		assertTrue(CreateTask.compareMonth(20,8));
	}

	@Test
	public void testDates(){
		assertEquals(CreateTask.dates("monday"),2);
	}

	@Test
	public void testMonths(){
		assertEquals(CreateTask.months("January"),0);
	}

	@Test
	public void testIsTime(){
		assertTrue(CreateTask.isTime("1230"));
		assertTrue(CreateTask.isTime("1230pm"));
		assertTrue(CreateTask.isTime("12:30pm"));
		assertTrue(CreateTask.isTime("midnight"));
		assertTrue(CreateTask.isTime("noon"));
	}

	@Test
	public void testContainsOnlyNumbers(){
		assertTrue(CreateTask.containsOnlyNumbers("112332"));
		assertFalse(CreateTask.containsOnlyNumbers("sf12"));
	}

	@Test
	public void testCompareTwoTime(){
		Time time1 = new Time(9,30);
		Time time2 = new Time(8,30);
		assertTrue(CreateTask.compareTwoTime(time1,time2));
	}


}
