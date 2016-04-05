
package main.parser;

import java.util.ArrayList;
import java.util.Calendar;

import main.resources.Date;
import main.resources.Task;
import main.resources.Time;
import main.parser.Shortcuts;

public class createTask {
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

	public final static Task createDeadline(String taskType, ArrayList<String> info) {
		String taskName = taskType + " task";
		Task task = new Task();
		int indexOfBy = info.indexOf(BY);
		int length = info.size();
		int indexOfP = 0;

		if (info.contains(PRIORITY)) {
			indexOfP = info.indexOf(PRIORITY);
			String priority = info.get(indexOfP + 1);
			task.setPriority(getPriority(priority));
		} else {
			indexOfP = length;
			task.setPriority(3);
		}

		String detail = getDetail(info, 1, indexOfBy);
		String dateAndTime = info.get(indexOfBy + 1);
		if (info.contains(AT)) {
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

		} else {
			Time time = getTime(dateAndTime);
			Date date = getDate(dateAndTime);
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskStartDate(date);
			task.setTaskStartTime(time);
			task.setTaskType(4);
		}
		return task;
	}

	public final static Task createEvent(String taskType, ArrayList<String> info) {
		String taskName = taskType + " task";
		Task task = new Task();
		int indexOfFrom = info.indexOf(FROM);
		int indexOfTo = info.indexOf(TO);
		int length = info.size();
		int indexOfP = 0;
		if (info.contains(PRIORITY)) {
			indexOfP = info.indexOf(PRIORITY);
			String priority = info.get(indexOfP + 1);
			task.setPriority(getPriority(priority));
		} else {
			indexOfP = length;
			task.setPriority(3);
		}

		String detail = getDetail(info, 1, indexOfFrom);
		String startDateAndTime = info.get(indexOfFrom + 1);
		String endDateAndTime = info.get(indexOfTo + 1);
		if (info.contains(AT)) {
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
		} else {
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
		return task;
	}

	public final static Task createFloating(String taskType, ArrayList<String> info) {
		String taskName = taskType + " task";
		Task task = new Task();
		int length = info.size();

		int indexOfP = 0;

		if (info.contains(AT)) { // info has location
			int indexOfAt = info.indexOf(AT);
			String detail = getDetail(info, 1, indexOfAt);
			String location = getLocation(info, indexOfAt + 1, indexOfP);
			if (info.contains(PRIORITY)) {
				indexOfP = info.indexOf(PRIORITY);
				String priority = info.get(indexOfP + 1);
				task.setPriority(getPriority(priority));
			} else {
				indexOfP = length;
				task.setPriority(3);
			}
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			;
			task.setTaskLocation(location);
			task.setTaskType(2);
		} else { // info does not have location
			String detail;
			if (info.contains(PRIORITY)) {
				indexOfP = info.indexOf(PRIORITY);
				detail = getDetail(info, 1, indexOfP);
				String priority = info.get(indexOfP + 1);
				task.setPriority(getPriority(priority));
			} else {
				indexOfP = length;
				task.setPriority(3);
				detail = getDetail(info, 1, length);
			}

			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskType(2);
		}
		return task;
	}

	public final static Task createRecurring(String taskType, ArrayList<String> info) {
		String taskName = taskType + " task";
		Task task = new Task();
		int indexOfFor = info.indexOf(FOR);
		int indexOfP = 0;
		String detail;
		String dateAndTime;
		String endDateAndTime;
		int fre = getFrequency(info.get(1));
		task.setRecurFrequency(fre);
		task.setRecurTime(Integer.parseInt(info.get(indexOfFor + 1)));

		if (info.contains(BY)) {
			int indexOfBy = info.indexOf(BY);
			detail = getDetail(info, 2, indexOfBy);
			dateAndTime = info.get(indexOfBy + 1);
			if (info.contains(AT)) {
				int indexOfAt = info.indexOf(AT);
				Time time = getTime(dateAndTime);
				Date date = getDate(dateAndTime);
				if (info.contains(PRIORITY)) {
					indexOfP = info.indexOf(PRIORITY);
					String priority = info.get(indexOfP + 1);
					task.setPriority(getPriority(priority));
					String location = getLocation(info, indexOfAt + 1, indexOfP);
					task.setTaskLocation(location);
				} else {
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

			} else {
				Time time = getTime(dateAndTime);
				Date date = getDate(dateAndTime);
				task.setTaskName(taskName);
				task.setTaskDetails(detail);
				task.setTaskStartDate(date);
				task.setTaskStartTime(time);
				task.setTaskType(4);

				if (info.contains(PRIORITY)) {
					indexOfP = info.indexOf(PRIORITY);
					String priority = info.get(indexOfP + 1);
					task.setPriority(getPriority(priority));
				} else {
					indexOfP = indexOfFor;
					task.setPriority(3);
				}
			}

		} else if (info.contains(FROM)) {
			int indexOfFrom = info.indexOf(FROM);
			int indexOfTo = info.indexOf(TO);
			detail = getDetail(info, 2, indexOfFrom);
			dateAndTime = info.get(indexOfFrom + 1);
			endDateAndTime = info.get(indexOfTo + 1);
			if (info.contains(AT)) {
				int indexOfAt = info.indexOf(AT);
				Time startTime = getTime(dateAndTime);
				Date startDate = getDate(dateAndTime);
				Time endTime = getTime(endDateAndTime);
				Date endDate = getDate(endDateAndTime);
				if (info.contains(PRIORITY)) {
					indexOfP = info.indexOf(PRIORITY);
					String priority = info.get(indexOfP + 1);
					String location = getLocation(info, indexOfAt + 1, indexOfP);
					task.setTaskLocation(location);
					task.setPriority(getPriority(priority));
				} else {
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

				// System.out.println(location);
				task.setTaskType(1);
			} else {
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
				if (info.contains(PRIORITY)) {
					indexOfP = info.indexOf(PRIORITY);
					String priority = info.get(indexOfP + 1);
					task.setPriority(getPriority(priority));
				} else {
					indexOfP = indexOfFor;
					task.setPriority(3);
				}
			}
		} else {
			if (info.contains(AT)) {
				int indexOfAt = info.indexOf(AT);
				detail = getDetail(info, 2, indexOfAt);
				String location = getLocation(info, indexOfAt + 1, indexOfP);
				task.setTaskName(taskName);
				task.setTaskDetails(detail);
				task.setTaskLocation(location);
				task.setTaskType(2);

			} else {
				if (info.contains(PRIORITY)) {
					indexOfP = info.indexOf(PRIORITY);
					detail = getDetail(info, 2, indexOfP);
					String priority = info.get(indexOfP + 1);
					task.setPriority(getPriority(priority));
				} else {
					indexOfP = indexOfFor;
					detail = getDetail(info, 2, indexOfP);
					task.setPriority(3);
				}
				task.setTaskName(taskName);
				task.setTaskDetails(detail);
				task.setTaskType(2);
			}
		}

		task.setRecurring(true);
		return task;
	}

	public static String getDetail(ArrayList<String> info, int start, int end) {
		String detail = "";
		for (int i = start; i < end; i++) {
			detail = detail + " " + info.get(i);
		}
		return detail.trim();
	}

	public static String getLocation(ArrayList<String> info, int start, int end) {
		String location = "";
		for (int i = start; i < end; i++) {
			location = location + " " + info.get(i);
		}
		return location.trim();
	}

	public static Time getTime(String dateAndTime) {
		Time time = new Time();
		if (dateAndTime.contains(";")) { // has both time and date
			String dAndT[] = dateAndTime.split(";");
			//System.out.println(dAndT[0]);
			if (!isTime(dAndT[0])) { // date first then time
				handleDiffTimeFormat(dAndT[1], time);
			} else { // time first then date
				//System.out.println("check isTimr");
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

	private static void handleDiffTimeFormat(String timeInfo, Time time) {
		if (timeInfo.contains(":")) {
			String hAndM[] = timeInfo.split(":");
			time.setHour(Integer.parseInt(hAndM[0]));
			time.setMinute(Integer.parseInt(hAndM[1]));
		} else if (timeInfo.toLowerCase().contains("am") || timeInfo.toLowerCase().contains("pm")) {
			if (timeInfo.toLowerCase().contains("am")) {
				String h[] = timeInfo.toLowerCase().split("am");
				time.setHour(Integer.parseInt(h[0]));
				time.setMinute(0);
			} else {
				String h[] = timeInfo.toLowerCase().split("pm");
				time.setHour(Integer.parseInt(h[0]) + 12);
				time.setMinute(0);
			}
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

	public static Date getDate(String dateAndTime) {
		Date date = new Date();
		if (dateAndTime.contains(";")) { // has both time and date
			//System.out.println("i want to see");
			String dAndT[] = dateAndTime.split(";");
			if (!isTime(dAndT[0])) { // date first then time
				//System.out.println("i donot want to see");
				handleDiffDateFormat(dAndT[0], date);
			} else { // time first then date
				//System.out.println("i want to see");
				handleDiffDateFormat(dAndT[1], date);
			}
		} else { // has either time or date
			if (!isTime(dateAndTime)) { // only have date info
				//System.out.println("ÎÒ²ÙÄãÂè±Æ");
				handleDiffDateFormat(dateAndTime, date);
			} else { // does not have date info
				Time t1 = getTime(dateAndTime);
				Time t2 = new Time(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
						Calendar.getInstance().get(Calendar.MINUTE));
				if (compareTwoTime(t1, t2)) {
					Calendar cal = Calendar.getInstance();
					date.setDay(cal.get(Calendar.DAY_OF_MONTH));
					date.setMonth(cal.get(Calendar.MONTH) + 1);
					date.setYear(cal.get(Calendar.YEAR));
				} else {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, 1);
					date.setDay(cal.get(Calendar.DAY_OF_MONTH));
					date.setMonth(cal.get(Calendar.MONTH) + 1);
					date.setYear(cal.get(Calendar.YEAR));
				}
			}
		}
		return date;
	}

	private static void handleDiffDateFormat(String dateInfo, Date date) {
		if (dateInfo.contains("/")) {
			String dmy[] = dateInfo.split("/");
			date.setDay(Integer.parseInt(dmy[0]));
			date.setMonth(Integer.parseInt(dmy[1]));
			date.setYear(Integer.parseInt(dmy[2]));
		} else if (Shortcuts.diffDateFormat(dateInfo).contains(TOMORROW)) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 1);
			date.setDay(cal.get(Calendar.DAY_OF_MONTH));
			date.setMonth(cal.get(Calendar.MONTH) + 1);
			date.setYear(cal.get(Calendar.YEAR));
		} else if (dates(Shortcuts.diffDateFormat(dateInfo)) != -1) {
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
		} else {
			System.out.println(Shortcuts.diffDateFormat(dateInfo));
			if (dateInfo.toLowerCase().contains("st")) {
				String dAndM[] = dateInfo.toLowerCase().split("st-");
				dAndM[1] = dAndM[1].trim();
				Calendar cal = Calendar.getInstance();
				if (months(Shortcuts.diffDateFormat(dAndM[1])) >= (cal.get(Calendar.MONTH) + 1)) {
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
				if (months(Shortcuts.diffDateFormat(dAndM[1])) >= (cal.get(Calendar.MONTH) + 1)) {
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
				if (months(Shortcuts.diffDateFormat(dAndM[1])) >= (cal.get(Calendar.MONTH) + 1)) {
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
				String dAndM[] = dateInfo.toLowerCase().split("th-");
				dAndM[1] = dAndM[1].trim();
				System.out.println(dAndM[1]);
				Calendar cal = Calendar.getInstance();
				if (months(Shortcuts.diffDateFormat(dAndM[1])) >= (cal.get(Calendar.MONTH))) {
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
	}

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

	private static boolean isTime(String timeInfo) {
		if (timeInfo.toLowerCase().contains("am") || timeInfo.toLowerCase().contains("pm")
				|| timeInfo.contains(MIDNIGHT) || timeInfo.contains(NOON) || containsOnlyNumbers(timeInfo)) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean containsOnlyNumbers(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	private static boolean compareTwoTime(Time t1, Time t2) {
		int t1TotalM = t1.getHour() * 60 + t1.getMinute();
		int t2TotalM = t2.getHour() * 60 + t2.getMinute();
		if (t1TotalM > t2TotalM) {
			return true;
		} else {
			return false;
		}
	}

	public static int getPriority(String p) {
		return Integer.parseInt(p);
	}

	public static int getFrequency(String fre) {
		if (fre.equals(DAILY)) {
			return 1;
		} else if (fre.equals(WEEKLY)) {
			return 2;
		} else if (fre.equals(MONTHLY)) {
			return 3;
		} else {
			return 4;
		}
	}
}
