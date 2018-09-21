package com.nico.springboot.quartz.crawler;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nico.springboot.entity.Bilibili;
import com.nico.springboot.service.BilibiliService;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import okhttp3.Request;

/**
 * 教程：使用WebCollector自定义Http请求 可以自定义User-Agent和Cookie
 *
 * @author hu
 */
@Component
public class BilibiliCrawler extends BreadthCrawler {
	@Autowired
	BilibiliService bilibiliService;
	
	// 自定义的请求插件
	// 可以自定义User-Agent和Cookie
	public static class MyRequester extends OkHttpRequester {

		String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.15 Safari/537.36";
		String cookie = "finger=d580c5e9; LIVE_BUVID=AUTO1915372551151458; fts=1537255120; buvid3=9BF60655-A42A-4441-BB12-5527DF4C328327983infoc; sid=jhf31nme; DedeUserID=10335624; DedeUserID__ckMd5=ab7f13f2b485e76b; SESSDATA=4874647a%2C1540016804%2Cce0c752c; bili_jct=d5618182c0d27525aa73850dc2c0b8fd; _dfcaptcha=5d7ba70cadc4587f88b96b260e43bc44";

		// 每次发送请求前都会执行这个方法来构建请求
		@Override
		public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
			// 这里使用的是OkHttp中的Request.Builder
			// 可以参考OkHttp的文档来修改请求头
			System.out.println("request with cookie: " + cookie);
			return super.createRequestBuilder(crawlDatum).addHeader("User-Agent", userAgent).addHeader("Cookie",
					cookie);
		}

	}
	
	public BilibiliCrawler() {
		this("bilibili1");
		
	}
	
	public BilibiliCrawler(String crawlPath) {
		super(crawlPath, true);

		// 设置请求插件
		setRequester(new MyRequester());
		MyRequester ms=new MyRequester();
		// 爬取github about下面的网页
		addSeed("https://www.bilibili.com");
		addRegex("https://www.bilibili.com/.*");

	}
	
	public void visit(Page page, CrawlDatums nextPost) {
		System.out.println(page.doc().title());
		
		String fileName=page.doc().title();
		Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
		Matcher matcher = pattern.matcher(fileName);
		fileName= matcher.replaceAll("").replace("_哔哩哔哩(゜-゜)つロ干杯~-bilibili", ""); // 将匹配到的非法字符以空替换
		
		String url=page.url();
		
		String html=page.html();//.replaceAll("/r", "").replaceAll("/n", "");
		//System.err.println("原生:/n"+html);
		Pattern pattern1 = Pattern.compile("__INITIAL_STATE__[\\s\\S]*?(?=};)");
		Matcher matcher1 = pattern1.matcher(html);
		matcher1.find();
		html=matcher1.group(0);
		html=html.substring(html.indexOf("{"));
		html=html+"}";
		JSONObject object=JSONObject.parseObject(html);
		//System.err.println("JSON:"+object.toJSONString());
		if(object.get("recommendData")!=null){
			List<String> parse = JSONArray.parseArray(object.get("recommendData").toString(),String.class);
			for (String string : parse) {
				JSONObject child=JSONObject.parseObject(string);
				try {
					Bilibili bilibili=new Bilibili();
					bilibili.setAid(child.get("aid").toString());
					bilibili.setTitle(child.get("title").toString());
					bilibili.setSource(html);
					Object desc = child.get("desc");
					bilibili.setContent(desc==null?"":desc.toString());
					Object description = child.get("description");
					bilibili.setContent(description==null?"":description.toString());
					
					bilibili.setPic(child.get("pic").toString());
					bilibili.setUrl(url);
					bilibiliService.insert(bilibili);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		if(object.get("videoData")!=null){
			JSONObject child=JSONObject.parseObject(object.get("videoData").toString());
			try {
				Bilibili bilibili=new Bilibili();
				bilibili.setAid(child.get("aid").toString());
				bilibili.setTitle(child.get("title").toString());
				bilibili.setSource(html);
				bilibili.setContent(child.get("desc").toString());
				bilibili.setPic(child.get("pic").toString());
				
				bilibili.setUrl(url);
				bilibiliService.insert(bilibili);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		BilibiliCrawler crawler = new BilibiliCrawler("bilibili1");
		crawler.setThreads(10);
		crawler.getConf().setExecuteInterval(1000);
		crawler.start(5);
		
	}
}
