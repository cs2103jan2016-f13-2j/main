package main.gui.resources;

public class Time {
	
	int hour;
	int minute;
	
	public Time(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public String getTime() {
		return hour+":"+minute;
	}
	
	public void setHour(int newHour) {
		hour = newHour;
	}
	
	public void setMinute(int newMinute) {
		minute = newMinute;
	}
	
	public void setTime(int newHour, int newMinute) {
		hour = newHour;
		minute = newMinute;
	}

}
