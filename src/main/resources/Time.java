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
	 * @param hour : the hour integer.
	 * @param minute : the minute integer.
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

	/**
	 * Retrieves the time in HH:MM string format. 
	 * @return the time string.
	 */
	public String getTimeString() {
		if (minute < 10) {
			return hour + ":0" + minute;
		}

		return hour + ":" + minute;
	}

	/**
	 * Sets the hour variable.
	 * @param hour : The hour integer.
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}

	/**
	 * Sets the minute variable.
	 * @param minute : The minute integer.
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}

	/**
	 * Sets the time variable.
	 * @param hour : The hour integer.
	 * @param minute : The minute integer.
	 */
	public void setTime(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
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
	 * Custom comparator for time.
	 * @param time : The time to compare with.
	 */
	@Override
	public int compareTo(Time time) {
		if (this.hour != time.getHour()) {
			return this.hour - time.getHour();
		}

		else {
			return this.minute - time.getMinute();
		}
	}

	/**
	 * Compares if two times are equal.
	 * @param time : The time to compare with.
	 * @return true if equal, false otherwise.
	 */
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
	
	//===== Private methods =====

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
}
