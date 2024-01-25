package ski.alto.cockpit.server.response;

public enum ResponseMessage {
	RESPONSE_MESSAGE_WRONG_EMAIL("You've entered a wrong email. Please check your spelling and try again."),
	RESPONSE_MESSAGE_WRONG_PASSWORD("You've entered a wrong password. Please check your spelling and try again.");
	
	private ResponseMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		return message;
	}
	
	private String message;
}
