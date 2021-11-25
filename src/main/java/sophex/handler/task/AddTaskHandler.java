package sophex.handler.task;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.http.task.AddTaskRequest;
import sophex.http.task.AddTaskResponse;

public class AddTaskHandler implements RequestHandler<AddTaskRequest, AddTaskResponse>{

	@Override
	public AddTaskResponse handleRequest(AddTaskRequest input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}


}
