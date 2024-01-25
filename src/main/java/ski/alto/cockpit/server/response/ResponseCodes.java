package ski.alto.cockpit.server.response;

public enum ResponseCodes {
	RESPONSE_CODE_OK(0),
	RESPONSE_CODE_WRONG_EMAIL(100),
	RESPONSE_CODE_WRONG_PASSWORD(101);
	
	private ResponseCodes(int value) {
        this.value = value;
    }
	
	public Integer getValue() {
		return value;
	}
	
	private Integer value;

}
