package com.nico.springboot.quartz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nico.springboot.quartz.crawler.BilibiliCrawler;

import cn.edu.hfut.dmic.webcollector.util.FileUtils;

@Component
public class TestTask {
	@Autowired
	BilibiliCrawler crawler;
	
	//@Scheduled(initialDelay=1000,fixedRate = 1000*60*5)
	@Scheduled(cron="0 59 9 * * ?")
	public void run(){
		//BilibiliCrawler crawler = new BilibiliCrawler("bilibili1");
		System.err.println("任务启动中···············");
		crawler.setThreads(10);
		try {
			crawler.start(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron="*0/10 * * * * ?")
	public void run1(){
		//BilibiliCrawler crawler = new BilibiliCrawler("bilibili1");
		System.err.println("测试任务启动中···············");
	}
	
	
	public static void main(String[] args) throws Exception {
		String html = FileUtils.read("E:/bilibili/111.html", "utf-8");
		//System.out.println(html);//window.__INITIAL_STATE__ = \\{*\\};
		Pattern pattern = Pattern.compile("__INITIAL_STATE__[\\s\\S]*?(?=};)");
		Matcher matcher = pattern.matcher(html);
		matcher.find();
		System.err.println(matcher.group(0));
	}
}
