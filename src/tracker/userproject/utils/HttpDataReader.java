package tracker.userproject.utils;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public class HttpDataReader {

	public static JSONObject convertRawInputToJsonObject(HttpServletRequest request) {
		return convertStringToJSONObject(convertInputDataToString(request));
	}

	public static String convertInputDataToString(HttpServletRequest request) {
		StringBuffer jb = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		try {
			reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jb.toString();
	}
	
	public static JSONObject convertStringToJSONObject(String jsonString) {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
}
