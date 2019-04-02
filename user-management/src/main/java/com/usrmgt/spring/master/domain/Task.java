package com.usrmgt.spring.master.domain;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import okhttp3.Response;

public class Task {
	private final AtomicInteger counter;
	private final DeferredResult<ResponseEntity<AggregateUserResponse>> result;
	private final List<AtomicRegisterRequest> requests;
	private final List<UserResponse> responses;
	private long startTime;

	public Task(final DeferredResult<ResponseEntity<AggregateUserResponse>> result, final List<AtomicRegisterRequest> requests) {
		this.counter = new AtomicInteger(requests.size());
		this.requests = requests;
		this.result = result;
		this.responses = requests.stream().map(s -> new UserResponse()).collect(toList());
	}
	
	public List<AtomicRegisterRequest> getRequests() {
        return unmodifiableList(requests);
    }
	
	public void fail(int index, long time,IOException e) {
		responses.get(index).sethttpResponseStatus(502);
        responses.get(index).setbody("Failed: " + e.getMessage());
        responses.get(index).setduration((int)(System.currentTimeMillis() - time));
        checkDone();
	}
	
	public void success(int index, long time, Response response) throws IOException {
		responses.get(index).sethttpResponseStatus(response.code());
        responses.get(index).setbody(response.body().string());
        responses.get(index).setduration((int)(System.currentTimeMillis() - time));
        checkDone();
	}
	
	public void start() {
        startTime = System.currentTimeMillis();
    }
	
	private void checkDone() {
		synchronized (counter) {
			if(counter.decrementAndGet() == 0) {
				AggregateUserResponse response = new AggregateUserResponse(responses, (int)(System.currentTimeMillis() - startTime));
				result.setResult(ResponseEntity.ok(response));
			}
		}		
	}
}
