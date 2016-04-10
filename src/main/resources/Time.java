package main.resources;

import java.io.Serializable;

//@@author A0124711U

public class Time implements Comparable<Time>, Serializable {

	private int hour;
	private int minute;
	
	/**
	 * Initialises the class variables to the defaults of -1.
	 */
	public Time() {
		this(-1, -1);
	}

	/**
	 * Initialises the class variables with the given input.
	 * @param hour
	 * @param minute
	 */
	public Time(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}

	//===== Public methods =====
	
	/**
	 * Retrieves the hour integer.
	 * @return the hour integer.
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * Retrieves the minute integer.
	 * @return the minute integer.
	 */
	public int getMinute() {
		return minute;
	}

	
	public Time getTime() {
		return this;
	}

	public String getTimeString() {
		if (minute < 10) {
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

	/**
	 * Checks whether the hour and minute are valid values.
	 * @return true if the time is valid, false otherwise.
	 */
	public boolean isValid() {
		if (isValidHour() && isValidMinute()) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if the hour is within the valid range.
	 * @return true if hour is valid, false otherwise.
	 */
	private boolean isValidHour() {
		if (hour >= 0 && hour <= 23) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if the minute is within the valid range.
	 * @return true if minute is valid, false otherwise.
	 */
	private boolean isValidMinute() {
		if (minute >= 0 && minute <= 59) {
			return true;
		}

		return false;
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
