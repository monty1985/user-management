package com.usrmgt.spring.master.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.usrmgt.spring.master.domain.AtomicRegisterRequest;
import com.usrmgt.spring.master.domain.Task;
import com.usrmgt.spring.master.utils.ServiceUtils;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@Service
public class UserService {
	   
   private OkHttpClient client = new OkHttpClient();

    public void executeRegister(final Task task) {        
        task.start();
        for(int i = 0; i < task.getRequests().size(); i++) {
            final int index = i;
            final long time = System.currentTimeMillis();            
            AtomicRegisterRequest atomicReq = task.getRequests().get(i);            
            Request req = new ServiceUtils(atomicReq).buildRequestForRegisterUser();            
//            logger.info("Sending request for the atomicReq userid: ["+ atomicReq.getuserId() +"] Access: [" + atomicReq.getaccess());
            client.newCall(req).enqueue(new Callback() {            
                @Override
                public void onFailure(Call request, IOException e) {
                    task.fail(index, time, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    task.success(index, time, response);
                }
            });
        }
    }	
}
