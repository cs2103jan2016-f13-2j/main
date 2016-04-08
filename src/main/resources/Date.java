package main.resources;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author A0125255L

public class Date implements Comparable<Date>, Serializable {

	int day;
	int month;
	int year;
	
	private static Logger logger = Logger.getLogger("Date");

	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		if (year < 2000) {
			year += 2000;
		}
		this.year = year;
	}

	public Date() {
		this(-1, -1, -1);
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

	//@@author A0124711U
	/**
	 * Checks whether the date is a valid calendar date.
	 * @return true if date is valid calendar date, false otherwise.
	 */
	public boolean isValid() {
		if (isValidYear() && isValidMonth()
				&& isValidDay()) {
			
			return true;
		}

		return false;
	}
	
	/**
	 * Checks if the year is a valid integer.
	 * @return true if valid integer, false otherwise.
	 */
	private boolean isValidYear() {
		if (year >= 0) {
			return true;
		}

		return false;
	}
	
	/**
	 * Checks if the month is within the valid range.
	 * @return true if month is valid, false otherwise.
	 */
	private boolean isValidMonth() {
		if (month >= 1 && month <= 12) {
			return true;
		}

		return false;
	}

	/**
	 * Checks if the day is within the valid range.
	 * @return true if day is valid, false otherwise.
	 */
	private boolean isValidDay() {
		if (day >= 1 && day <= maxDayOfMonth()) {
			return true;
		}

		return false;
	}
	
	/**
	 * Retrieves the maximum number of days in a month, given a valid month.
	 * @return int : Maximum number of days in the month.
	 */
	private int maxDayOfMonth() {
		assert(isValidMonth());
		
		switch (month) {
		case 1:
			return 31;

		case 2:
			if (isLeapYear()) {
				return 29;
			}
			else {
				return 28;
			}

		case 3:
			return 31;

		case 4:
			return 30;

		case 5:
			return 31;

		case 6:
			return 30;

		case 7:
			return 31;

		case 8:
			return 31;

		case 9:
			return 30;

		case 10:
			return 31;

		case 11:
			return 30;

		case 12:
			return 31;

		default:
			logger.log(Level.SEVERE, "Invalid month entered in maxMonthOfDay()");
		}
		
		return -1;
	}

	/**
	 * Calculates if the year is a leap year, given a valid year.
	 * @return true if year is leap year, false otherwise.
	 */
	private boolean isLeapYear() {
		assert(isValidYear());
		
		if (year % 4 != 0 || year % 100 == 0 && year % 400 != 0) {
			return false;
		}
		
		return true;
	}

	//@@author A0125255L
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
