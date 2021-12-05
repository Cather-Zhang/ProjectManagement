package sophex.http.project;
import java.util.ArrayList;

import sophex.model.Teammate;

public class TeamViewResponse {
		public int statusCode;
		public String error;
		public ArrayList<Teammate> teammates;
		
		public TeamViewResponse (ArrayList<Teammate> teammates) {
			this.teammates = teammates;
			this.statusCode = 200;
			this.error = "";
		}
		
		public TeamViewResponse (int statusCode, String errorMessage) {
			this.statusCode = statusCode;
			this.error = errorMessage;
		}
		
		/*public String toString() {
			if (statusCode / 100 == 2) {  // too cute?
				return project.getname() + " view success!";
			} else {
				return "ErrorResult(" + statusCode + ", err=" + error + ")";
			}
		}*/
}
