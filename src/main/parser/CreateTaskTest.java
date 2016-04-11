package main.parser;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;
//@@author A0133926A



import main.resources.Date;
import main.resources.Task;
import main.resources.Time;
import main.parser.Parser;
import main.parser.CreateTask;


public class CreateTaskTest {
	
	@Test
	public void testCreateDeadline(){
		String taskType = "deadline";
		String command = "add eating by 20th-Aug;6pm at soc -p 1";
		Date date = new Date(20,8,2016);
		Time time = new Time(18,0);
		ArrayList<String> info = Parser.retrieveCommand(command);
		Task task = CreateTask.createDeadline(taskType,info);
		assertEquals(task.getTaskName(),"deadline task");
		assertEquals(task.getTaskDetails(),"eating");
		assertEquals(task.getTaskStartTime(),time);
		assertEquals(task.getTaskStartDate(),date);
		assertEquals(task.getTaskLocation(),"soc");
		assertEquals(task.getPriority(),1);
	}

	@Test
	public void testCreateEvent(){
		String taskType = "event";
		String command = "add eating from 20th-Aug;6pm to 21st-Aug;6pm at soc -p 1";
		Date startDate = new Date(20,8,2016);
		Time startTime = new Time(18,0);
		Date endDate = new Date(21,8,2016);
		Time endTime = new Time(18,0);
		ArrayList<String> info = Parser.retrieveCommand(command);
		Task task = CreateTask.createEvent(taskType,info);
		assertEquals(task.getTaskName(),"event task");
		assertEquals(task.getTaskDetails(),"eating");
		assertEquals(task.getTaskStartTime(),startTime);
		//assertEquals(task.getTaskStartDate(),startDate);
		//assertEquals(task.getTaskEndTime(),endTime);
		//assertEquals(task.getTaskEndDate(),endDate);
		//assertEquals(task.getTaskLocation(),"soc");
		//assertEquals(task.getPriority(),1);
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
		Date startDate = new Date(20,8,2016);
		Time startTime = new Time(18,0);
		Date endDate = new Date(21,8,2016);
		Time endTime = new Time(18,0);
		ArrayList<String> info = Parser.retrieveCommand(command);
		Task task = CreateTask.createRecurring(taskType,info);
		assertEquals(task.getTaskName(),"event task");
		assertEquals(task.getTaskDetails(),"eating");
		assertEquals(task.getTaskStartTime(),startTime);
		assertEquals(task.getTaskStartDate(),startDate);
		assertEquals(task.getTaskEndTime(),endTime);
		assertEquals(task.getTaskEndDate(),endDate);
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
		Time time = new Time(18,0);
		assertEquals(CreateTask.getTime(dateAndTime),time);
	}

	@Test
	public void testGetDate(){
		String dateAndTime = "20th-Aug;6pm";
		Date date = new Date(20,8,2016);
		assertEquals(CreateTask.getDate(dateAndTime),date);
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
