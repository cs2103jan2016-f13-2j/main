

package main.parser;


import java.util.ArrayList;
import java.util.Calendar;

import main.resources.Date;
import main.resources.Task;
import main.resources.Time;




public class createTask {
	private static final String FROM = "from";
	private static final String BY = "by";
	private static final String TO = "to";
	private static final String AT = "at";
	private static final String PRIORITY = "p";
	private static final String DAILY = "daily";
	private static final String WEEKLY = "weekly";
	private static final String MONTHLY = "monthly";
	private static final String YEARLY = "yealy";
	private static final String FOR = "for";
	
		
	public final static Task createDeadline(String taskType, ArrayList<String> info) {
		String taskName = taskType+" task";
		Task task = new Task();
		int indexOfBy = info.indexOf(BY);
		int length = info.size();
		int indexOfP = 0;
		
		if(info.contains(PRIORITY)){
			indexOfP = info.indexOf(PRIORITY);
			String priority = info.get(indexOfP+1);
			task.setPriority(getPriority(priority));
		} else {
			indexOfP = length;
			task.setPriority(3);
		}

		String detail = getDetail(info,1,indexOfBy);
		String dateAndTime = info.get(indexOfBy+1);
		if(info.contains(AT)){
			int indexOfAt = info.indexOf(AT);
			Time time = getTime(dateAndTime);
			Date date = getDate(dateAndTime);
			String location = getLocation(info,indexOfAt+1,indexOfP);
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
		String taskName = taskType+" task";
		Task task = new Task();
		int indexOfFrom = info.indexOf(FROM);
		int indexOfTo = info.indexOf(TO);
		int length = info.size();
		int indexOfP = 0;
		if(info.contains(PRIORITY)){
			indexOfP = info.indexOf(PRIORITY);
			String priority = info.get(indexOfP+1);
			task.setPriority(getPriority(priority));
		} else {
			indexOfP = length;
			task.setPriority(3);
		}
		
		String detail = getDetail(info,1,indexOfFrom);
		String startDateAndTime = info.get(indexOfFrom+1);
		String endDateAndTime = info.get(indexOfTo+1);
		if(info.contains(AT)){
			int indexOfAt = info.indexOf(AT);
			Time startTime = getTime(startDateAndTime);
			Date startDate = getDate(startDateAndTime);
			Time endTime = getTime(endDateAndTime);
			Date endDate = getDate(endDateAndTime);
			String location = getLocation(info,indexOfAt+1,indexOfP);
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
		String taskName = taskType+" task";
		Task task = new Task();
		int length = info.size();
		
		
		int indexOfP = 0;

		
		if(info.contains(AT)){ //info has location
			int indexOfAt = info.indexOf(AT);
			String detail = getDetail(info,1,indexOfAt);
			String location = getLocation(info,indexOfAt+1,indexOfP);
			if(info.contains(PRIORITY)){
				indexOfP = info.indexOf(PRIORITY);
				String priority = info.get(indexOfP+1);
				task.setPriority(getPriority(priority));
			} else {
				indexOfP = length;
				task.setPriority(3);
			}
			task.setTaskName(taskName);
			task.setTaskDetails(detail);;
			task.setTaskLocation(location);
			task.setTaskType(2);
		} else { //info does not have location
			String detail;
			if(info.contains(PRIORITY)){
				indexOfP = info.indexOf(PRIORITY);
				detail = getDetail(info,1,indexOfP);
				String priority = info.get(indexOfP+1);
				task.setPriority(getPriority(priority));
			} else {
				indexOfP = length;
				task.setPriority(3);
				detail = getDetail(info,1,length);
			}
			
			task.setTaskName(taskName);
			task.setTaskDetails(detail);
			task.setTaskType(2);
		}
		return task;
	}
	
	public final static Task createRecurring(String taskType, ArrayList<String> info) {
		String taskName = taskType+" task";
		Task task = new Task();
		int indexOfFor = info.indexOf(FOR);
		int indexOfP = 0;
		String detail;
		String dateAndTime;
		int fre = getFrequency(info.get(1));
		task.setRecurFrequency(fre);
		task.setRecurTime(Integer.parseInt(info.get(indexOfFor+1)));
		
		
		if(info.contains(BY)){
			int indexOfBy = info.indexOf(BY);
			detail = getDetail(info,2,indexOfBy);
			dateAndTime = info.get(indexOfBy+1);
			if(info.contains(AT)){
				int indexOfAt = info.indexOf(AT);
				Time time = getTime(dateAndTime);
				Date date = getDate(dateAndTime);
				String location = getLocation(info,indexOfAt+1,indexOfP);
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
			
			if(info.contains(PRIORITY)){
				indexOfP = info.indexOf(PRIORITY);
				String priority = info.get(indexOfP+1);
				task.setPriority(getPriority(priority));
			} else {
				indexOfP = indexOfFor;
				task.setPriority(3);
			}

		} else {
			if(info.contains(AT)){
				int indexOfAt = info.indexOf(AT);
				detail = getDetail(info,2,indexOfAt);
				String location = getLocation(info,indexOfAt+1,indexOfP);
				task.setTaskName(taskName);
				task.setTaskDetails(detail);
				task.setTaskLocation(location);
				task.setTaskType(2);
				
			} else {
				if(info.contains(PRIORITY)){
					indexOfP = info.indexOf(PRIORITY);
					detail = getDetail(info,2,indexOfP);
					String priority = info.get(indexOfP+1);
					task.setPriority(getPriority(priority));
				} else {
					indexOfP = indexOfFor;
					detail = getDetail(info,2,indexOfP);
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
	
	public static String getDetail(ArrayList<String> info, int start, int end){
		String detail = "";
		for(int i=start; i<end; i++){
			detail = detail + " "+info.get(i);
		}
		return detail.trim();
	}
	
	public static String getLocation(ArrayList<String> info, int start, int end){
		String location = "";
		for(int i=start; i<end; i++){
			location = location + " "+info.get(i);
		}
		return location.trim();
	}


	
	public static Time getTime(String dateAndTime){
		Time time = new Time();
		if(dateAndTime.contains(";")){  //has both time and date
			String dAndT[] = dateAndTime.split(";");
			if(dAndT[0].contains("/")){  //date first then time			
				handleDiffTimeFormat(dAndT[1],time);
			} else {   //time first then date
				handleDiffTimeFormat(dAndT[0],time);
			}
		} else {  //has either time or date
			if(dateAndTime.contains("/")){ //does not have time info
				time = null;
			} else {  //only has time
				handleDiffTimeFormat(dateAndTime,time);
			}
		}
		return time;
	}
	
	private static void handleDiffTimeFormat(String timeInfo,Time time){
		if(timeInfo.contains(":")){
			String hAndM[] = timeInfo.split(":");
			time.setHour(Integer.parseInt(hAndM[0]));
			time.setMinute(Integer.parseInt(hAndM[1]));
		} else if(timeInfo.contains("m")){
			if(timeInfo.contains("am")){
				String h[] = timeInfo.split("am");
				time.setHour(Integer.parseInt(h[0]));
				time.setMinute(0);
			} else {
				String h[] = timeInfo.split("pm");
				time.setHour(Integer.parseInt(h[0])+12);
				time.setMinute(0);
			}
		} else {
			if(timeInfo.length()==3){
				time.setHour(Integer.parseInt(timeInfo.substring(0,1)));
				time.setMinute(Integer.parseInt(timeInfo.substring(1,3)));
			} else {
				time.setHour(Integer.parseInt(timeInfo.substring(0,2)));
				time.setMinute(Integer.parseInt(timeInfo.substring(2,4)));
			}
		}

	}


	public static Date getDate(String dateAndTime){
		Date date = new Date();
		if(dateAndTime.contains(";")){  //has both time and date
			String dAndT[] = dateAndTime.split(";");
			if(dAndT[0].contains("/")){  //date first then time
				String dmy[] = dAndT[0].split("/");
				date.setDay(Integer.parseInt(dmy[0]));
				date.setMonth(Integer.parseInt(dmy[1]));
				date.setYear(Integer.parseInt(dmy[2]));
			} else {   //time first then date
				String dmy[] = dAndT[1].split("/");
				date.setDay(Integer.parseInt(dmy[0]));
				date.setMonth(Integer.parseInt(dmy[1]));
				date.setYear(Integer.parseInt(dmy[2]));
			}
		} else {  //has either time or date
			if(dateAndTime.contains("/")){ //only have date info
				String dmy[] = dateAndTime.split("/");
				date.setDay(Integer.parseInt(dmy[0]));
				date.setMonth(Integer.parseInt(dmy[1]));
				date.setYear(Integer.parseInt(dmy[2]));
			} else {  //does not have time info
				Time t1 = getTime(dateAndTime);
				Time t2 = new Time(Calendar.getInstance().get(Calendar.HOUR),Calendar.getInstance().get(Calendar.MINUTE));
				if(compareTwoTime(t1,t2)){
					date.setDay(Calendar.getInstance().get(Calendar.DATE));
					date.setMonth(Calendar.getInstance().get(Calendar.MONTH)+1);
					date.setYear(Calendar.getInstance().get(Calendar.YEAR));
				} else {
					date.setDay(Calendar.getInstance().get(Calendar.DATE)+1);
					date.setMonth(Calendar.getInstance().get(Calendar.MONTH)+1);
					date.setYear(Calendar.getInstance().get(Calendar.YEAR));
				}
			}
		}
		return date;
	}
	
	private static boolean compareTwoTime(Time t1, Time t2){
		int t1TotalM = t1.getHour()*60+t1.getMinute();
		int t2TotalM = t2.getHour()*60+t2.getMinute();
		if(t1TotalM>t2TotalM){
			return true;
		} else {
			return false;
		}
	}
	
	public static int getPriority(String p){
		return Integer.parseInt(p);
	}
	
	public static int getFrequency(String fre){
		if(fre.equals(DAILY)){
			return 1;
		} else if(fre.equals(WEEKLY)){
			return 2;
		} else if(fre.equals(MONTHLY)){
			return 3;
		} else {
			return 4;
		}
	}
}
