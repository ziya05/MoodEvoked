package com.ziya05.MoodEvoked.backend.service;

import java.io.IOException;

import com.ziya05.MoodEvoked.backend.pojo.Result;

public interface ResultService {
	void SaveResult(Result result) throws IOException;
}
