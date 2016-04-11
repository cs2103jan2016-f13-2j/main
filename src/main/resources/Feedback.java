package main.resources;
//@@author A0124711U
public class Feedback {
	private static Feedback feedback;

	private String message;
	private String sortString;

	private Feedback() {
		//empty
	}

	/**
	 * Creates a Feedback object for use if it doesn't exist.
	 * @return the Feedback object.
	 */
	public static Feedback getInstance() {
		if (feedback == null) {
			feedback = new Feedback();
		}

		return feedback;
	}

	/**
	 * Retrieves the feedback message.
	 * @return the feedback message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Retrieves the string entered as the sort term.
	 * @return the string entered as the sort term.
	 */
	public String getSortString() {
		return sortString;
	}

	/**
	 * Sets the feedback message to be displayed.
	 * @param feedback message
	 */
	public void setMessage(String message) {
		if (this.message == null || message == null) {
			this.message = message;
		}
	}

	/**
	 * Sets the sort string that was entered as the sort term.
	 * @param sort term
	 */
	public void setSortString(String string) {
		this.sortString = string;
	}
}
