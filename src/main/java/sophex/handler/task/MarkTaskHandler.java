package sophex.handler.task;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import sophex.http.task.MarkTaskRequest;
import sophex.http.task.MarkTaskResponse;

public class MarkTaskHandler implements RequestHandler<MarkTaskRequest, MarkTaskResponse>{

	@Override
	public MarkTaskResponse handleRequest(MarkTaskRequest input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}


}
