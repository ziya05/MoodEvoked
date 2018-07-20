package com.ziya05.MoodEvoked.backend.restful;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ziya05.MoodEvoked.backend.pojo.JSONResult;
import com.ziya05.MoodEvoked.backend.pojo.Result;
import com.ziya05.MoodEvoked.backend.service.ResultService;

@RestController
@RequestMapping("/mood")
public class MoodApi {

	@Autowired
	private ResultService resultService;
	
	@CrossOrigin
	@RequestMapping(value = "/save", method = RequestMethod.POST)  
	public JSONResult saveResult(@RequestBody Result result) throws IOException {
		System.out.println("数据已接收， 将保存！");
		result.print(); 
		
		resultService.SaveResult(result);
		return JSONResult.ok("保存成功！");
	}
}
