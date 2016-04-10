
package main.parser;

import java.util.ArrayList;
import java.util.Calendar;

import main.resources.Date;
import main.resources.Task;
import main.resources.Time;
import main.parser.Shortcuts;

//@@author A0133926A

public class CreateTask {
	private static final String FROM = "from";
	private static final String BY = "by";
	private static final String TO = "to";
	private static final String AT = "at";
	private static final String PRIORITY = "-p";
	private static final String DAILY = "daily";
	private static final String WEEKLY = "weekly";
	private static final String MONTHLY = "monthly";
	private static final String YEARLY = "yealy";
	private static final String FOR = "for";
	private static final String MIDNIGHT = "midnight";
	private static final String NOON = "noon";
	private static final String TOMORROW = "tomorrow";
	private static final String TODAY = "today";
	private static final String MONDAY = "monday";
	private static final String TUESDAY = "tuesday";
	private static final String WEDNESDAY = "wednesday";
	private static final String THURSDAY = "thursday";
	private static final String FRIDAY = "friday";
	private static final String SATURDAY = "saturday";
	private static final String SUNDAY = "sunday";
	private static final String JANUARY = "January";
	private static final String FEBRUARY = "February";
	private static final String MARCH = "March";
	private static final String APRIL = "April";
	private static final String MAY = "May";
	private static final String JUNE = "June";
	private static final String JULY = "July";
	private static final String AUGUST = "August";
	private static final String SEPTEMBER = "September";
	private static final String OCTOBER = "October";
	private static final String NOVEMBER = "November";
	private static final String DECEMBER = "December";

	
	/**
	 * create the deadline task
	 * @param taskType
	 * @param info
	 * @return a deadline task
	 */
	public final static Task createDeadline(String taskType, ArrayList<String> info) {
		String taskName = taskType + " task";
		Task task = new Task();
		int indexOfBy = info.indexOf(BY);
		int length = info.size();
		int indexOfP = 0;

		indexOfP = setPriorityForDeadlineTask(info, task, length);

		String detail = getDetail(info, 1, indexOfBy);
		String dateAndTime = info.get(indexOfBy + 1);
		setLocationForDeadlineTask(info, taskName, task, indexOfP, detail, dateAndTime);
		return task;
	}

	/**
	 * assign location,start time, start date, task details to deadline task
	 * @param info
	 * @param taskName
	 * @param task
	 * @param indexOfP
	 * @param detail
	 * @param dateAndTime
	 */
	private static void setLocationForDeadlineTask(ArrayList<String> info, String taskName, Task task, int indexOfP,
			String detail, String dateAndTime) {
		if (info.contains(AT)) {//if has location, assign it
			int indexOfAt = info.indexOf(AT);
			Time time = getTime(dateAndTime);
			Date date = getDate(dateAndTime);
			String location = getLocation(info, indexOfAt + 1, indexOfP);
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskStartDate(date);
			task.setTaskStartTime(time);
			task.setTaskLocation(location);
			task.setTaskType(4);

		} else {//do not have location
			Time time = getTime(dateAndTime);
			Date date = getDate(dateAndTime);
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskStartDate(date);
			task.setTaskStartTime(time);
			task.setTaskType(4);
		}
	}

	/**
	 * assign priority to deadline task
	 * @param info
	 * @param task
	 * @param length
	 * @return the index of the keyword priority in the arraylist
	 */
	private static int setPriorityForDeadlineTask(ArrayList<String> info, Task task, int length) {
		int indexOfP;
		if (info.contains(PRIORITY)) {//if has priority, assign it
			indexOfP = info.indexOf(PRIORITY);
			String priority = info.get(indexOfP + 1);
			task.setPriority(getPriority(priority));
		} else {//do not indicate priority, set the priority as 3(lowest priority)
			indexOfP = length;
			task.setPriority(3);
		}
		return indexOfP;
	}

	/**
	 * create the event task
	 * @param taskType
	 * @param info
	 * @return an event task
	 */
	public final static Task createEvent(String taskType, ArrayList<String> info) {
		String taskName = taskType + " task";
		Task task = new Task();
		int indexOfFrom = info.indexOf(FROM);
		int indexOfTo = info.indexOf(TO);
		int length = info.size();
		int indexOfP = 0;
		indexOfP = setPriorityForDeadlineTask(info, task, length);

		String detail = getDetail(info, 1, indexOfFrom);
		String startDateAndTime = info.get(indexOfFrom + 1);
		String endDateAndTime = info.get(indexOfTo + 1);
		setLocationForEventTask(info, taskName, task, indexOfP, detail, startDateAndTime, endDateAndTime);
		return task;
	}

	
	/**
	 * assign the location,start time, end time, start date and end date to the event task
	 * @param info
	 * @param taskName
	 * @param task
	 * @param indexOfP
	 * @param detail
	 * @param startDateAndTime
	 * @param endDateAndTime
	 */
	private static void setLocationForEventTask(ArrayList<String> info, String taskName, Task task, int indexOfP,
			String detail, String startDateAndTime, String endDateAndTime) {
		if (info.contains(AT)) {//user indicate the location
			int indexOfAt = info.indexOf(AT);
			Time startTime = getTime(startDateAndTime);
			Date startDate = getDate(startDateAndTime);
			Time endTime = getTime(endDateAndTime);
			Date endDate = getDate(endDateAndTime);
			String location = getLocation(info, indexOfAt + 1, indexOfP);
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskStartDate(startDate);
			task.setTaskStartTime(startTime);
			task.setTaskEndDate(endDate);
			task.setTaskEndTime(endTime);
			task.setTaskLocation(location);
			task.setTaskType(1);
		} else {//do not indicate the location
			Time startTime = getTime(startDateAndTime);
			Date startDate = getDate(startDateAndTime);
			Time endTime = getTime(endDateAndTime);
			Date endDate = getDate(endDateAndTime);
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskStartDate(startDate);
			task.setTaskStartTime(startTime);
			task.setTaskEndDate(endDate);
			task.setTaskEndTime(endTime);
			task.setTaskType(1);
		}
	}
	
	/**
	 * create the floating task
	 * @param taskType
	 * @param info
	 * @return the floating task
	 */
	public final static Task createFloating(String taskType, ArrayList<String> info) {
		String taskName = taskType + " task";
		Task task = new Task();
		int length = info.size();
		int indexOfP = 0;
		setLocationForFloatingTask(info, taskName, task, length, indexOfP);
		return task;
	}

	/**
	 * assign the location and location to the floating task
	 * @param info
	 * @param taskName
	 * @param task
	 * @param length
	 * @param indexOfP
	 */
	private static void setLocationForFloatingTask(ArrayList<String> info, String taskName, Task task, int length,
			int indexOfP) {
		if (info.contains(AT)) { // info has location
			int indexOfAt = info.indexOf(AT);
			String detail = getDetail(info, 1, indexOfAt);
			String location = getLocation(info, indexOfAt + 1, indexOfP);
			indexOfP = setPriorityForDeadlineTask(info, task, length);
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskLocation(location);
			task.setTaskType(2);
		} else { // info does not have location
			String detail;
			detail = setPriorityForFloatingTask(info, task, length);
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskType(2);
		}
	}

	/**
	 * assign the priority to the floating task
	 * @param info
	 * @param task
	 * @param length
	 * @return the index number of the keyword priority in the arraylist
	 */
	private static String setPriorityForFloatingTask(ArrayList<String> info, Task task, int length) {
		int indexOfP;
		String detail;
		if (info.contains(PRIORITY)) {//has priority, set the priority to the task
			indexOfP = info.indexOf(PRIORITY);
			detail = getDetail(info, 1, indexOfP);
			String priority = info.get(indexOfP + 1);
			task.setPriority(getPriority(priority));
		} else {//do not indicate the priority, set the priority as 3(lowest priority)
			indexOfP = length;
			task.setPriority(3);
			detail = getDetail(info, 1, length);
		}
		return detail;
	}

	/**
	 * create recurring task
	 * @param taskType
	 * @param info
	 * @return a recurring task
	 */
	public final static Task createRecurring(String taskType, ArrayList<String> info) {
		String taskName = taskType + " task";
		Task task = new Task();
		int indexOfFor = info.indexOf(FOR);
		int indexOfP = 0;
		int fre = getFrequency(info.get(1));
		task.setRecurFrequency(fre);
		task.setRecurTime(Integer.parseInt(info.get(indexOfFor + 1)));

		if (info.contains(BY)) {//the recurring task is a deadline task
			createDeadlineRecurringTask(info, taskName, task, indexOfFor);

		} else if (info.contains(FROM)) {//the recurring task is a deadline task
			createEventRecurringTask(info, taskName, task, indexOfFor);
		} else {//the recurring task is a floating task
			createFloatingRecurringTask(info, taskName, task, indexOfFor, indexOfP);
		}

		task.setRecurring(true);
		return task;
	}

	/**
	 * create a floating recurring task
	 * @param info
	 * @param taskName
	 * @param task
	 * @param indexOfFor
	 * @param indexOfP
	 */
	private static void createFloatingRecurringTask(ArrayList<String> info, String taskName, Task task, int indexOfFor,
			int indexOfP) {
		String detail;
		if (info.contains(AT)) {//has location information
			int indexOfAt = info.indexOf(AT);
			detail = getDetail(info, 2, indexOfAt);
			String location = getLocation(info, indexOfAt + 1, indexOfP);
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskLocation(location);
			task.setTaskType(2);

		} else {//do not have location information
			if (info.contains(PRIORITY)) {//indicate priority
				indexOfP = info.indexOf(PRIORITY);
				detail = getDetail(info, 2, indexOfP);
				String priority = info.get(indexOfP + 1);
				task.setPriority(getPriority(priority));
			} else {//do not indicate priority
				indexOfP = indexOfFor;
				detail = getDetail(info, 2, indexOfP);
				task.setPriority(3);
			}
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskType(2);
		}
	}

	/**
	 * create an event recurring task
	 * @param info
	 * @param taskName
	 * @param task
	 * @param indexOfFor
	 */
	private static void createEventRecurringTask(ArrayList<String> info, String taskName, Task task, int indexOfFor) {
		int indexOfP;
		String detail;
		String dateAndTime;
		String endDateAndTime;
		int indexOfFrom = info.indexOf(FROM);
		int indexOfTo = info.indexOf(TO);
		detail = getDetail(info, 2, indexOfFrom);
		dateAndTime = info.get(indexOfFrom + 1);
		endDateAndTime = info.get(indexOfTo + 1);
		if (info.contains(AT)) {//indicate location
			int indexOfAt = info.indexOf(AT);
			Time startTime = getTime(dateAndTime);
			Date startDate = getDate(dateAndTime);
			Time endTime = getTime(endDateAndTime);
			Date endDate = getDate(endDateAndTime);
			if (info.contains(PRIORITY)) {//indicate priority
				indexOfP = info.indexOf(PRIORITY);
				String priority = info.get(indexOfP + 1);
				String location = getLocation(info, indexOfAt + 1, indexOfP);
				task.setTaskLocation(location);
				task.setPriority(getPriority(priority));
			} else {//do not indicate the priority
				indexOfP = indexOfFor;
				task.setPriority(3);
				String location = getLocation(info, indexOfAt + 1, indexOfFor);
				task.setTaskLocation(location);
			}
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskStartDate(startDate);
			task.setTaskStartTime(startTime);
			task.setTaskEndDate(endDate);
			task.setTaskEndTime(endTime);

			task.setTaskType(1);
		} else {//do not indicate location
			Time startTime = getTime(dateAndTime);
			Date startDate = getDate(dateAndTime);
			Time endTime = getTime(endDateAndTime);
			Date endDate = getDate(endDateAndTime);
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskStartDate(startDate);
			task.setTaskStartTime(startTime);
			task.setTaskEndDate(endDate);
			task.setTaskEndTime(endTime);
			task.setTaskType(1);
			indexOfP = setPriorityForDeadlineTask(info, task, indexOfFor);
		}
	}

	/**
	 * create a deadline recurring task
	 * @param info
	 * @param taskName
	 * @param task
	 * @param indexOfFor
	 */
	private static void createDeadlineRecurringTask(ArrayList<String> info, String taskName, Task task,
			int indexOfFor) {
		int indexOfP;
		String detail;
		String dateAndTime;
		int indexOfBy = info.indexOf(BY);
		detail = getDetail(info, 2, indexOfBy);
		dateAndTime = info.get(indexOfBy + 1);
		if (info.contains(AT)) {//indicate location
			int indexOfAt = info.indexOf(AT);
			Time time = getTime(dateAndTime);
			Date date = getDate(dateAndTime);
			if (info.contains(PRIORITY)) {//indicate priority
				indexOfP = info.indexOf(PRIORITY);
				String priority = info.get(indexOfP + 1);
				task.setPriority(getPriority(priority));
				String location = getLocation(info, indexOfAt + 1, indexOfP);
				task.setTaskLocation(location);
			} else {//do not indicate priority
				indexOfP = indexOfFor;
				task.setPriority(3);
				String location = getLocation(info, indexOfAt + 1, indexOfP);
				task.setTaskLocation(location);
			}
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskStartDate(date);
			task.setTaskStartTime(time);

			task.setTaskType(4);

		} else {//do not indicate location
			Time time = getTime(dateAndTime);
			Date date = getDate(dateAndTime);
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskStartDate(date);
			task.setTaskStartTime(time);
			task.setTaskType(4);

			indexOfP = setPriorityForDeadlineTask(info, task, indexOfFor);
		}
	}

	/**
	 * get task details from the arraylist
	 * @param info
	 * @param start
	 * @param end
	 * @return the string of details
	 */
	public static String getDetail(ArrayList<String> info, int start, int end) {
		String detail = "";
		for (int i = start; i < end; i++) {
			detail = detail + " " + info.get(i);
		}
		return detail.trim();
	}

	
	/**
	 * get task location from the arraylist
	 * @param info
	 * @param start
	 * @param end
	 * @return the string of location
	 */
	public static String getLocation(ArrayList<String> info, int start, int end) {
		String location = "";
		for (int i = start; i < end; i++) {
			location = location + " " + info.get(i);
		}
		return location.trim();
	}

	/**
	 * get time from the date and time string
	 * @param dateAndTime
	 * @return time object
	 */
	public static Time getTime(String dateAndTime) {
		Time time = new Time();
		if (dateAndTime.contains(";")) { // has both time and date
			String dAndT[] = dateAndTime.split(";");
			if (!isTime(dAndT[0])) { // date first then time
				handleDiffTimeFormat(dAndT[1], time);
			} else { // time first then date
				handleDiffTimeFormat(dAndT[0], time);
			}
		} else { // has either time or date
			if (!isTime(dateAndTime)) { // does not have time info
				time = null;
			} else { // only has time
				handleDiffTimeFormat(dateAndTime, time);
			}
		}
		return time;
	}

	/**
	 * deal with different time format
	 * @param timeInfo
	 * @param time
	 */
	private static void handleDiffTimeFormat(String timeInfo, Time time) {
		if(timeInfo.contains("am")||timeInfo.contains("pm")){
			handleDiffTimeFormatPart2(timeInfo,time);
		} else {
			handleDiffTimeFormatPart1(timeInfo,time);
		}
	}

	/**
	 * handle 4 time format: 12:00  midnight  noon and 1200 or 923
	 * @param timeInfo
	 * @param time
	 */
	private static void handleDiffTimeFormatPart1(String timeInfo,Time time){
		if (timeInfo.contains(":")) {
			String hAndM[] = timeInfo.split(":");
			time.setHour(Integer.parseInt(hAndM[0]));
			time.setMinute(Integer.parseInt(hAndM[1]));
		} else if (timeInfo.equals(MIDNIGHT)) {
			time.setHour(23);
			time.setMinute(59);
		} else if (timeInfo.equals(NOON)) {
			time.setHour(12);
			time.setMinute(0);
		} else {
			if (timeInfo.length() == 3) {
				time.setHour(Integer.parseInt(timeInfo.substring(0, 1)));
				time.setMinute(Integer.parseInt(timeInfo.substring(1, 3)));
			} else {
				time.setHour(Integer.parseInt(timeInfo.substring(0, 2)));
				time.setMinute(Integer.parseInt(timeInfo.substring(2, 4)));
			}
		}
	}

	/**
	 * handle the date format with "pm" and "am"
	 * @param timeInfo
	 * @param time
	 */
	private static void handleDiffTimeFormatPart2(String timeInfo,Time time){
		if (timeInfo.toLowerCase().contains("am")) {
			String h[] = timeInfo.toLowerCase().split("am");
			if(h[0].length()<=2){
				time.setHour(Integer.parseInt(h[0]));
				time.setMinute(0);
			} else {
				handleDiffTimeFormatPart1(h[0],time);
			}
		} else {
			String h[] = timeInfo.toLowerCase().split("pm");
			if(h[0].length()<=2){
				time.setHour(Integer.parseInt(h[0])+12);
				time.setMinute(0);
			} else {
				handleDiffTimeFormatPart1(h[0],time);
				time.setHour(time.getHour()+12);
			}
		}
	}

	/**
	 * get date from the date and time string
	 * @param dateAndTime
	 * @return the date object
	 */
	public static Date getDate(String dateAndTime) {
		Date date = new Date();
		if (dateAndTime.contains(";")) { // has both time and date
			String dAndT[] = dateAndTime.split(";");
			if (!isTime(dAndT[0])) { // date first then time
				handleDiffDateFormat(dAndT[0], date);
			} else { // time first then date
				handleDiffDateFormat(dAndT[1], date);
			}
		} else { // has either time or date
			if (!isTime(dateAndTime)) { // only have date info

				handleDiffDateFormat(dateAndTime, date);
			} else { // does not have date info
				Time t1 = getTime(dateAndTime);
				Time t2 = new Time(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
						Calendar.getInstance().get(Calendar.MINUTE));
				if (compareTwoTime(t1, t2)) {
					handleDateFormatWithToday(date);
				} else {
					handleDateFormatWithTmr(date);
				}
			}
		}
		return date;
	}

	/**
	 * handle different date format
	 * @param dateInfo
	 * @param date
	 */
	private static void handleDiffDateFormat(String dateInfo, Date date) {
		if (dateInfo.contains("/")) {
			handleDateFormatWithBackSlash(dateInfo, date);		
		} else if(Shortcuts.diffDateFormat(dateInfo).contains(TODAY)) {
			handleDateFormatWithToday(date);
		} else if (Shortcuts.diffDateFormat(dateInfo).contains(TOMORROW)) {
			handleDateFormatWithTmr(date);
		} else if (dates(Shortcuts.diffDateFormat(dateInfo)) != -1) {
			handleDateFormatWithWeekday(dateInfo, date);
		} else {
			handleDateFormatWithDateAndMonth(dateInfo, date);
		}
	}

	/**
	 * handle date format like this: 1st-apr or 11-aug
	 * @param dateInfo
	 * @param date
	 */
	private static void handleDateFormatWithDateAndMonth(String dateInfo, Date date) {
		if (dateInfo.toLowerCase().contains("st")) {
			String dAndM[] = dateInfo.toLowerCase().split("st-");
			dAndM[1] = dAndM[1].trim();
			Calendar cal = Calendar.getInstance();
			if (compareMonth(Integer.parseInt(dAndM[0]),months(Shortcuts.diffDateFormat(dAndM[1]))+1)) {
				date.setDay(Integer.parseInt(dAndM[0]));
				date.setMonth(months(Shortcuts.diffDateFormat(dAndM[1]))+1);
				date.setYear(cal.get(Calendar.YEAR));
			} else {
				cal.add(Calendar.YEAR, 1);
				date.setDay(Integer.parseInt(dAndM[0]));
				date.setMonth(months(Shortcuts.diffDateFormat(dAndM[1]))+1);
				date.setYear(cal.get(Calendar.YEAR));
			}
		} else if (dateInfo.toLowerCase().contains("nd")) {
			String dAndM[] = dateInfo.toLowerCase().split("nd-");
			dAndM[1] = dAndM[1].trim();
			Calendar cal = Calendar.getInstance();
			if (compareMonth(Integer.parseInt(dAndM[0]),months(Shortcuts.diffDateFormat(dAndM[1]))+1)) {
				date.setDay(Integer.parseInt(dAndM[0]));
				date.setMonth(months(Shortcuts.diffDateFormat(dAndM[1]))+1);
				date.setYear(cal.get(Calendar.YEAR));
			} else {
				cal.add(Calendar.YEAR, 1);
				date.setDay(Integer.parseInt(dAndM[0]));
				date.setMonth(months(Shortcuts.diffDateFormat(dAndM[1]))+1);
				date.setYear(cal.get(Calendar.YEAR));
			}
		} else if (dateInfo.toLowerCase().contains("rd")) {
			String dAndM[] = dateInfo.toLowerCase().split("rd-");
			dAndM[1] = dAndM[1].trim();
			Calendar cal = Calendar.getInstance();
			if (compareMonth(Integer.parseInt(dAndM[0]),months(Shortcuts.diffDateFormat(dAndM[1]))+1)) {
				date.setDay(Integer.parseInt(dAndM[0]));
				date.setMonth(months(Shortcuts.diffDateFormat(dAndM[1]))+1);
				date.setYear(cal.get(Calendar.YEAR));
			} else {
				cal.add(Calendar.YEAR, 1);
				date.setDay(Integer.parseInt(dAndM[0]));
				date.setMonth(months(Shortcuts.diffDateFormat(dAndM[1]))+1);
				date.setYear(cal.get(Calendar.YEAR));
			}
		} else if(dateInfo.toLowerCase().contains("th")){
			String dAndM[] = dateInfo.toLowerCase().split("th-");
			dAndM[1] = dAndM[1].trim();
			Calendar cal = Calendar.getInstance();
			if (compareMonth(Integer.parseInt(dAndM[0]),months(Shortcuts.diffDateFormat(dAndM[1]))+1)) {
				date.setDay(Integer.parseInt(dAndM[0]));
				date.setMonth(months(Shortcuts.diffDateFormat(dAndM[1]))+1);
				date.setYear(cal.get(Calendar.YEAR));
			} else {
				cal.add(Calendar.YEAR, 1);
				date.setDay(Integer.parseInt(dAndM[0]));
				date.setMonth(months(Shortcuts.diffDateFormat(dAndM[1]))+1);
				date.setYear(cal.get(Calendar.YEAR));
			}
		} else {
			String dAndM[] = dateInfo.toLowerCase().split("-");
			dAndM[1] = dAndM[1].trim();
			Calendar cal = Calendar.getInstance();
			if (compareMonth(Integer.parseInt(dAndM[0]),months(Shortcuts.diffDateFormat(dAndM[1]))+1)) {
				date.setDay(Integer.parseInt(dAndM[0]));
				date.setMonth(months(Shortcuts.diffDateFormat(dAndM[1]))+1);
				date.setYear(cal.get(Calendar.YEAR));
			} else {
				cal.add(Calendar.YEAR, 1);
				date.setDay(Integer.parseInt(dAndM[0]));
				date.setMonth(months(Shortcuts.diffDateFormat(dAndM[1]))+1);
				date.setYear(cal.get(Calendar.YEAR));
			}
		}
	}

	/**
	 * handle date format like: Monday, Tuesday etc.
	 * @param dateInfo
	 * @param date
	 */
	private static void handleDateFormatWithWeekday(String dateInfo, Date date) {
		Calendar cal = Calendar.getInstance();
		int infoDayOfWeek = dates(Shortcuts.diffDateFormat(dateInfo));
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (infoDayOfWeek >= dayOfWeek) {
			cal.add(Calendar.DAY_OF_MONTH, infoDayOfWeek - dayOfWeek);
			date.setDay(cal.get(Calendar.DAY_OF_MONTH));
			date.setMonth(cal.get(Calendar.MONTH) + 1);
			date.setYear(cal.get(Calendar.YEAR));
		} else {
			cal.add(Calendar.DAY_OF_MONTH, infoDayOfWeek - dayOfWeek + 7);
			date.setDay(cal.get(Calendar.DAY_OF_MONTH));
			date.setMonth(cal.get(Calendar.MONTH) + 1);
			date.setYear(cal.get(Calendar.YEAR));
		}
	}

	/**
	 * handle date format like: tmr or tmrw or tomorrow
	 * @param date
	 */
	private static void handleDateFormatWithTmr(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		date.setDay(cal.get(Calendar.DAY_OF_MONTH));
		date.setMonth(cal.get(Calendar.MONTH) + 1);
		date.setYear(cal.get(Calendar.YEAR));
	}

	/**
	 * handle date format like: today
	 * @param date
	 */
	private static void handleDateFormatWithToday(Date date) {
		Calendar cal = Calendar.getInstance();
		date.setDay(cal.get(Calendar.DAY_OF_MONTH));
		date.setMonth(cal.get(Calendar.MONTH) + 1);
		date.setYear(cal.get(Calendar.YEAR));
	}

	/**
	 * handle date format like: 06/08/2016 or 06/08
	 * @param dateInfo
	 * @param date
	 */
	private static void handleDateFormatWithBackSlash(String dateInfo, Date date) {
		String dmy[] = dateInfo.split("/");
		date.setDay(Integer.parseInt(dmy[0]));
		date.setMonth(Integer.parseInt(dmy[1]));
		if(dmy.length==3){
			date.setYear(Integer.parseInt(dmy[2]));
		} else {
			if(compareMonth(Integer.parseInt(dmy[0]),Integer.parseInt(dmy[1]))){
				Calendar cal = Calendar.getInstance();
				date.setYear(cal.get(Calendar.YEAR));
			} else{
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.YEAR, 1);
				date.setYear(cal.get(Calendar.YEAR));
			}
		}
	}

	/**
	 * compare two date, witch only contains month and date
	 * @param date
	 * @param month
	 * @return if the date is larger or equal to today's date, return true; or return false 
	 */
	private static boolean compareMonth(int date, int month){
		Calendar cal = Calendar.getInstance();
		int curDate = cal.get(Calendar.DAY_OF_MONTH);
		int curMonth = cal.get(Calendar.MONTH)+1;
		if(month>curMonth){
			return true;
		} else if(month==curMonth){
			if(date>=curDate){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Translate the weekday to a number,Sunday to Saturday indicate 1 to 7
	 * @param dateInfo
	 * @return the integer number
	 */
	private static int dates(String dateInfo) {
		int a = 0;
		switch (dateInfo) {
		case MONDAY:
			a = 2;
			break;
		case TUESDAY:
			a = 3;
			break;
		case WEDNESDAY:
			a = 4;
			break;
		case THURSDAY:
			a = 5;
			break;
		case FRIDAY:
			a = 6;
			break;
		case SATURDAY:
			a = 7;
			break;
		case SUNDAY:
			a = 1;
			break;
		default:
			a = -1;
			break;
		}
		return a;
	}

	/**
	 * Translate the month to a number, Jan to Dec indicate 0 to 11
	 * @param dateInfo
	 * @return the integer number
	 */
	private static int months(String dateInfo) {
		int a = 0;
		switch (dateInfo) {
		case JANUARY:
			a = 0;
			break;
		case FEBRUARY:
			a = 1;
			break;
		case MARCH:
			a = 2;
			break;
		case APRIL:
			a = 3;
			break;
		case MAY:
			a = 4;
			break;
		case JUNE:
			a = 5;
			break;
		case JULY:
			a = 6;
			break;
		case AUGUST:
			a = 7;
			break;
		case SEPTEMBER:
			a = 8;
			break;
		case OCTOBER:
			a = 9;
			break;
		case NOVEMBER:
			a = 10;
			break;
		case DECEMBER:
			a = 11;
			break;
		default:
			a = -1;
			break;
		}
		return a;
	}

	/**
	 * judge if the string is time or date
	 * @param timeInfo
	 * @return true if the string is time
	 */
	private static boolean isTime(String timeInfo) {
		if (timeInfo.toLowerCase().contains("am") || timeInfo.toLowerCase().contains("pm")
				|| timeInfo.contains(MIDNIGHT) || timeInfo.contains(NOON) || containsOnlyNumbers(timeInfo)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * to check whether the string only contains number
	 * @param str
	 * @return true if the string only has number
	 */
	private static boolean containsOnlyNumbers(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * give 2 time with hour and minute
	 * @param t1
	 * @param t2
	 * @return true if the first time is bigger than the second one
	 */
	private static boolean compareTwoTime(Time t1, Time t2) {
		int t1TotalM = t1.getHour() * 60 + t1.getMinute();
		int t2TotalM = t2.getHour() * 60 + t2.getMinute();
		if (t1TotalM > t2TotalM) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * get priority from the string
	 * @param p 
	 * @return integer number
	 */
	public static int getPriority(String p) {
		int n = Integer.parseInt(p);
		if(n>=3){
			return 3;
		} else {
			return n;
		}
	}

	/**
	 * get frequency from the string 
	 * @param fre
	 * @return integer number
	 */
	public static int getFrequency(String fre) {
		if (fre.equals(DAILY)) {
			return 1;
		} else if (fre.equals(WEEKLY)) {
			return 2;
		} else if (fre.equals(MONTHLY)) {
			return 3;
		} else if(fre.equals(YEARLY)){
			return 4;
		} else {
			return -1;
		}
	}
}
