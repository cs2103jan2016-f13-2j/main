package main.resources;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author A0124711U

public class Date implements Comparable<Date>, Serializable {

	private int day;
	private int month;
	private int year;

	private static Logger logger = Logger.getLogger("Date");

	/**
	 * Initialises the class variables to the defaults of -1.
	 */
	public Date() {
		this(-1, -1, -1);
	}

	/**
	 * Initialises the class variables with the given input.
	 * @param day : The integer day.
	 * @param month : The integer month.
	 * @param year : The integer year.
	 */
	public Date(int day, int month, int year) {
		if (year < 2000) {
			year += 2000;
		}

		this.day = day;
		this.month = month;
		this.year = year;
	}

	//===== Public methods =====

	/**
	 * Retrieves the day integer.
	 * @return the day integer.
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Retrieves the month integer.
	 * @return the day integer.
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Retrieves the year integer.
	 * @return the year integer.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Retrieves the date in DD-MM-YYYY string format.
	 * @return the date string.
	 */
	public String getDateString() {
		return day + "-" + month + "-" + year;
	}

	/**
	 * Sets the day variable.
	 * @param day : The day integer.
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * Sets the month variable.
	 * @param month : The month integer.
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * Sets the year variable.
	 * @param year : The year integer.
	 */
	public void setYear(int year) {
		if (year < 2000) {
			year += 2000;
		}

		this.year = year;
	}

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
	 * Custom comparator for dates.
	 * @param date : The date to compare with.
	 */
	@Override
	public int compareTo(Date date) {
		if (year != date.getYear()) {
			return year - date.getYear();
		}
		else if (month != date.getMonth()) {
			return month - date.getMonth();
		}
		else {
			return day - date.getDay();
		}
	}

	/**
	 * Compares if two dates are equal.
	 * @param date : The date to compare with.
	 * @return True if equal, false otherwise.
	 */
	public boolean equals(Date date){
		if (date == null) {
			return false;
		}
		else {
			if(date instanceof Date) {
				Date obj = (Date) date;
				return year == obj.getYear() && 
						month == obj.getMonth() && 
						day == obj.getDay();
			}
			
			return false;
		}
	}
	
	//===== Private methods =====

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
}