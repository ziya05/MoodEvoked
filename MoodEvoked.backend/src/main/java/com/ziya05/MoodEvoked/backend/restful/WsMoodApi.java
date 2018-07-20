package com.ziya05.MoodEvoked.backend.restful;

import java.io.IOException;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.ziya05.MoodEvoked.backend.configuration.MyEndpointConfigure;
import com.ziya05.MoodEvoked.backend.pojo.Item;
import com.ziya05.MoodEvoked.backend.pojo.JSONResult;
import com.ziya05.MoodEvoked.backend.pojo.Result;
import com.ziya05.MoodEvoked.backend.service.ResultService;

@ServerEndpoint(value = "/mood/save",configurator = MyEndpointConfigure.class)
@Component
public class WsMoodApi {

	@Autowired
	private ResultService resultService;
	
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		
		System.out.println("打开一个会话！" + session.getId());
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println(message);
		
		Gson gson = new Gson();
		JSONResult jsonResult = null;
		
		try
		{
			
			Result result = gson.fromJson(message, Result.class);
			
			resultService.SaveResult(result);	
			
			jsonResult = JSONResult.ok("保存成功！");
			String data = gson.toJson(jsonResult);
			session.getBasicRemote().sendText(data);
			
		}
		catch(Exception ex) {
			jsonResult = JSONResult.errorMsg("保存失败！" + ex.toString());
			String data = gson.toJson(jsonResult);
			session.getBasicRemote().sendText(data);
			
			throw ex;
		}
		
	}
	
	@OnClose
	public void onClose() {
		System.out.println("会话关闭了！" + session.getId());
	}
}
