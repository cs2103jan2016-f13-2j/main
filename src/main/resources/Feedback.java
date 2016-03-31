package main.resources;

public class Feedback {
	private static Feedback feedback;
	
	private String message;
	
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
	
	public void setMessage(String message) {
		this.message = message;
	}
}
