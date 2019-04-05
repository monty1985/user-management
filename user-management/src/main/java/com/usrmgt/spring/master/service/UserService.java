package com.usrmgt.spring.master.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.usrmgt.spring.master.domain.AtomicDeleteRequest;
import com.usrmgt.spring.master.domain.AtomicRegisterRequest;
import com.usrmgt.spring.master.domain.DeleteTask;
import com.usrmgt.spring.master.domain.Task;
import com.usrmgt.spring.master.utils.Host;
import com.usrmgt.spring.master.utils.ManageHostImpl;
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
    	
    	final Map<String,List<Host>> hostMap = ManageHostImpl.gethostAccessMap();
        task.start();
        for(int i = 0; i < task.getRequests().size(); i++) {
            final int index = i;
            final long time = System.currentTimeMillis();            
            AtomicRegisterRequest atomicReq = task.getRequests().get(i);            
            Host reqHost = hostMap.get(atomicReq.getaccess()).get(0);            
            Request req = ServiceUtils.buildRequestForRegisterUser(atomicReq, reqHost); 
            client.newCall(req).enqueue(new Callback() {            
                @Override
                public void onFailure(Call request, IOException e) {
                    task.fail(index, time, req.url().toString(), atomicReq.getaccess(),e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    task.success(index, time, req.url().toString(),  atomicReq.getaccess(), response);
                }
            });
        }
    }	
    
    
    public void executeDelete(final DeleteTask delTask) { 
    	final Map<String,List<Host>> hostMap = ManageHostImpl.gethostAccessMap();
    	delTask.start();
        for(int i = 0; i < delTask.getRequests().size(); i++) {
            final int index = i;
            final long time = System.currentTimeMillis();            
            AtomicDeleteRequest atomicDelReq = delTask.getRequests().get(i); 
            Host reqHost = hostMap.get(atomicDelReq.getaccess()).get(0);
            Request req = ServiceUtils.buildRequestForDeleteUser(atomicDelReq,reqHost);            
            client.newCall(req).enqueue(new Callback() {            
            	@Override
                public void onFailure(Call request, IOException e) {
            		delTask.fail(index, time, req.url().toString(), atomicDelReq.getaccess(),e);
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                	delTask.success(index, time, req.url().toString(),  atomicDelReq.getaccess(), response);
                }
            });
        }
    }    
}
