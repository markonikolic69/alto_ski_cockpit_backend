package ski.alto.cockpit.server.utility;

public class OwnershipUtil {

    public static final String SKICLUB_GB = "SKICLUB_GB";

    public static String parseOwnership(String ownership) {
    	if(ownership == null) {
    		return null;
    	}else {
    		if(ownership.trim().equals("")) {
    			return null;
    		}else {
    			return "SkiClub";
    		}
    	}
//        if (ownership != null /*&& ownership.equals(SKICLUB_GB)*/) {
//            return "SkiClub";
//        } else {
//            return null;
//        }
    }
}
