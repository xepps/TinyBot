package com.xepps.tinyBot;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler
{
	private static final String SITE = "http://yuftee.com"; 
	
	ArrayList<String> queue;
	HashMap<String, Integer> pages;
	private int queueLocation;
	
	public Crawler()
	{
		queueLocation = 0;
		queue = new ArrayList<String>();
		pages = new HashMap<String, Integer>();
		
		queue.add(SITE);
		pages.put(SITE, 1);
	}
	
	private void retrievePage() throws IOException
	{
		System.out.println("On Page: " + this.queue.get(queueLocation));
		
		if(!this.queue.get(queueLocation).startsWith(SITE))
			return;
		
		Document doc = Jsoup.connect(this.queue.get(queueLocation)).get();
		
		Elements links = doc.getElementsByTag("a");
		
		for(Element link : links)
			addToQueue(link);		
	}

	private void addToQueue(Element link)
	{
		if(!pages.containsKey(link.attr("href")))
		{
			pages.put(link.attr("href"), 1);
			queue.add(link.attr("href"));
		}
		else
		{
			Integer hits = pages.get(link.attr("href"));
			pages.remove(link.attr("href"));
			pages.put(link.attr("href"), (hits += 1));
		}
	}
	
	public void crawl() throws IOException, InterruptedException
	{
		do
		{
			this.retrievePage();
			queueLocation++;
			Thread.sleep(1 * 1000);
		}
		while(queue.size() > queueLocation);
	}
	
	public ArrayList<String> getCrawledPages()
	{
		return this.queue;
	}
	
	public HashMap<String, Integer> getHits()
	{
		return this.pages;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		Crawler crawler = new Crawler();
		crawler.crawl();
		
		HashMap<String, Integer> pages = crawler.getHits();
		ArrayList<String> urls = crawler.getCrawledPages();
		
		PrintWriter out = new PrintWriter("pages.csv", "UTF-8");
		out.println("HITS,\tPAGE");
		for(String url : urls)
			out.println(pages.get(url) + ",\t" + url);
		
		out.close();

	}
}