package main.resources;
//@@author A0124711U
public class Feedback {
	private static Feedback feedback;

	private String message;
	private String sortString;

	private Feedback() {
		//empty
	}

	public static Feedback getInstance() {
		if (feedback == null) {
			feedback = new Feedback();
		}

		return feedback;
	}

	public String getMessage() {
		return message;
	}

	public String getSortString() {
		return sortString;
	}

	public void setMessage(String message) {
		if (message != null) {
			this.message = message;
		}
	}

	public void setSortString(String string) {
		this.sortString = string;
	}
}
