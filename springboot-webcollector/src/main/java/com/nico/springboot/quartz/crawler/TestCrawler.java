package com.nico.springboot.quartz.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nico.springboot.entity.Bilibili;
import com.nico.springboot.mapper.BilibiliMapper;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

//@Component
public class TestCrawler extends BreadthCrawler{
	@Autowired
	BilibiliMapper bilibiliMapper;
	
	public TestCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		addSeed("https://www.bilibili.com/");
		
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		Bilibili bilibili=new Bilibili();
		
		bilibiliMapper.insert(bilibili);
		
	}

}
