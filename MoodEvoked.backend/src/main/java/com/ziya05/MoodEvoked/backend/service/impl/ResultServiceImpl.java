package com.ziya05.MoodEvoked.backend.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ziya05.MoodEvoked.backend.pojo.Result;
import com.ziya05.MoodEvoked.backend.service.ResultService;

@Service
public class ResultServiceImpl implements ResultService {

	@Value("${my.result.path}")
	private String resultPath;
	
	@Override
	public void SaveResult(Result result) throws IOException {

		String path = resultPath + this.GetCurrentDate();
		
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String fileName = String.format("%s/%s-%s.txt", 
				path,
				result.getUser().getText(),
				this.GetCurrentTime());
		
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		Gson gson = new Gson();
        bw.write(gson.toJson(result));
        bw.close();
        fw.close();

	}

	private String GetCurrentDate() {
		return this.GetTimeString("yyyyMMdd");
	}
	
	private String GetCurrentTime() {
		return this.GetTimeString("HHmmss");
	}
	
	private String GetTimeString(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(cal.getTime());
	}
}
