package main.gui.resources;

public class Date {
	
	int day;
	int month;
	int year;
	
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getDate() {
		return day+"-"+month+"-"+year;
	}
	
	public void setDay(int newDay) {
		day = newDay;
	}
	
	public void setMonth(int newMonth) {
		month = newMonth;
	}
	
	public void setYear(int newYear) {
		year = newYear;
	}
}
