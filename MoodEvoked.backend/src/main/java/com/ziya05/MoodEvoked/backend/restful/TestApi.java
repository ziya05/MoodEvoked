package com.ziya05.MoodEvoked.backend.restful;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApi {
	
	@RequestMapping("/get")
	public String get() {
		return "hello get";
	}
}
