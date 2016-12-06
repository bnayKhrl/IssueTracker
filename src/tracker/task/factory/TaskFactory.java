package tracker.task.factory;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import tracker.task.entity.Task;
import tracker.userproject.utils.HttpDataReader;

public class TaskFactory {
	public static List<Task> getDataFromJsonArray(HttpServletRequest req) {
		JSONObject jsonObj = HttpDataReader.convertRawInputToJsonObject(req);
		Task task = new Task();
		JSONArray jsonArray = jsonObj.getJSONArray("myrows");
		List<Task> tasklist = new ArrayList<Task>();
		for (int i = 0; i < jsonArray.length(); i++) {
			String tasks = jsonArray.get(i).toString();
			JSONObject jsonObjDetail = null;
			jsonObjDetail = new JSONObject(tasks);
			
			
			task.setName(jsonObjDetail.get("Task").toString());
			task.setStartdate(Time.valueOf(jsonObjDetail.get("Startdate").toString()));
			tasklist.add(task);
		}
		

		return tasklist;
	}

}
