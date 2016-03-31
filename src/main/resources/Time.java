package main.resources;

import java.io.Serializable;

public class Time implements Comparable<Time>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		if (minute == 0) {
			return hour+":0"+minute;
		}
		
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

	@Override
	public int compareTo(Time time) {
		if (this.hour != time.getHour()) {
			return this.hour - time.getHour();
		}
		
		else {
			return this.minute - time.getMinute();
		}
	}
	
	public boolean equals(Time time){
		  if (time == null) {
			  return false;
		  }
		  
		  else {
			  if(time instanceof Time) {
				  Time obj = (Time) time;
				  return this.hour == obj.getHour() && 
						  this.minute == obj.getMinute();
			  }
			  
			  return false;
		  }
		}

}
