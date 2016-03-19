package main.resources;

public class Time {
	
	int hour;
	int minute;
	
	public Time(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}
	
	public Time() {
		this(0,0);
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public Time getTime() {
		return this;
	}
	
	public String getTimeString() {
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
