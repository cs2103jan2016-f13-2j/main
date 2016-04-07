package main.resources;

import java.io.Serializable;

//@@author A0125255L

public class Date implements Comparable<Date>, Serializable {
	
	int day;
	int month;
	int year;
	
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		if (year < 2000) {
			year += 2000;
		}
		this.year = year;
	}
	
	public Date() {
		this(0,0,0);
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
	
	public Date getDate() {
		return this;
	}
	
	public String getDateString() {
		return day+"-"+month+"-"+year;
	}
	
	public void setDay(int newDay) {
		day = newDay;
	}
	
	public void setMonth(int newMonth) {
		month = newMonth;
	}
	
	public void setYear(int newYear) {
		if (newYear < 2000) {
			newYear += 2000;
		}
		this.year = newYear;
	}

	@Override
	public int compareTo(Date date) {
		if (this.year != date.getYear()) {
			return this.year - date.getYear();
		}
		else if (this.month != date.getMonth()) {
			return this.month - date.getMonth();
		}
		
		else {
			return this.day - date.getDay();
		}
	}
	
	public boolean equals(Date date){
	  if (date == null) {
		  return false;
	  }
	  
	  else {
		  if(date instanceof Date) {
			  Date obj = (Date) date;
			  return this.year == obj.getYear() && 
					  this.month == obj.getMonth() && 
					  this.day == obj.getDay();
		  }
		  return false;
	  }
	}
}
