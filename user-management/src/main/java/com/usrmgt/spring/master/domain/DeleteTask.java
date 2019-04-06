package com.usrmgt.spring.master.domain;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.usrmgt.spring.node.dto.AtomicDeleteRequest;

import okhttp3.Response;

public class DeleteTask {
	private final AtomicInteger counter;
	private final DeferredResult<ResponseEntity<AggregateUserResponse>> result;
	private final List<AtomicDeleteRequest> requests;
	private final List<HTTPResponseCollector> responses;
	private long startTime;

	public DeleteTask(final DeferredResult<ResponseEntity<AggregateUserResponse>> result, final List<AtomicDeleteRequest> requests) {
		this.counter = new AtomicInteger(requests.size());
		this.requests = requests;
		this.result = result;
		this.responses = requests.stream().map(s -> new HTTPResponseCollector()).collect(toList());
	}
	
	public List<AtomicDeleteRequest> getRequests() {
        return unmodifiableList(requests);
    }
	
	public void fail(int index, long time, String url, String access,  IOException e) {
		responses.get(index).sethttpResponseStatus(502);
        responses.get(index).setbody("Failed: " + e.getMessage());
        responses.get(index).setduration((int)(System.currentTimeMillis() - time));
        responses.get(index).settarget(url);
        responses.get(index).setaccess(access);
        checkDone();
	}
	
	public void success(int index, long time, String url, String access, Response response) throws IOException {
		responses.get(index).sethttpResponseStatus(response.code());
        responses.get(index).setbody(response.body().string());
        responses.get(index).setduration((int)(System.currentTimeMillis() - time));
        responses.get(index).settarget(url);
        responses.get(index).setaccess(access);
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
