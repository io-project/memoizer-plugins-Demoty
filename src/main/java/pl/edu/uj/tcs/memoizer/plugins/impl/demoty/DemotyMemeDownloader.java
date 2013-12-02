package pl.edu.uj.tcs.memoizer.plugins.impl.demoty;

import java.io.IOException;
import java.net.*;
import java.util.*;
import pl.edu.uj.tcs.memoizer.plugins.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * Plugin submodule designed for downloading 
 * and extracting memes from page sources
 */
class DemotyMemeDownloader {
	
	/*
	 * Get a page source, parse it,
	 * extract memes and return
	 */
	static List<Meme> downloadMemesFromPage(URL url, EViewType viewType, IPluginFactory pluginFactory){
		return extractMemesFromNodes(
				extractMemeNodes(
				downloadPageSource(url)), viewType, pluginFactory);
	}
	
	/*
	 * Download page source from specific url
	 * If success returns downloaded page
	 * otherwise returns null
	 */
	private static Document downloadPageSource(URL url){
		try{
			return Jsoup
					.connect(url.toString())
					.userAgent("Mozilla")
					.get();
		} catch(IOException e){}
		return null;
	}
	
	/*
	 * Extract html meme-linked elements 
	 * from given page source
	 */
	private static Elements extractMemeNodes(Document demotyPageSource){
		Elements result = new Elements();
		
		if(demotyPageSource == null)
			return result;
		
		Elements demotySection = demotyPageSource.select("section.demots");
		for(Element elem : demotySection){
			Elements demotivators = elem.select("div.demotivator[id]");
			result.addAll(demotivators);
		}
		
		return result;
	}
	
	/*
	 * Parse html meme-linked element and extract meme info
	 * returns list of parsed memes
	 */
	private static List<Meme> extractMemesFromNodes(Elements memeNodes, EViewType viewType, IPluginFactory pluginFactory){
		List<Meme> lst = new ArrayList<Meme>();
		
		for(Element meme : memeNodes){
			try{
				Element picLink = meme.select("a.picwrapper[href]").first();
				URL pageLink = extractPageLinkFromATag(picLink);
				
				Element image = picLink.select("img.demot[src]").first();
				URL imageLink = extractImageLinkFromImgTag(image);
				
				String fullTitle = extractTitleFromImgTag(image);
				int split = fullTitle.indexOf('–');
				
				String title = fullTitle.substring(0, split-1);
				String desc = fullTitle.substring(split+1, fullTitle.length()-1);
				
				int width = extractWidthFromImgTag(image);
				int heigth = extractHeightFromImgTag(image);
				
				if(imageLink != null)
					lst.add(new Meme(imageLink, pageLink, title, desc, width, heigth, null, viewType, pluginFactory));
			} catch(Exception e){}
		}
		
		return lst;
	}
	
	private static URL extractPageLinkFromATag(Element aTagElement){
		try{
			return new URL("http://www.demotywatory.pl" + aTagElement.attr("href"));
		}catch(Exception e){}
		return null;
	}
	
	private static URL extractImageLinkFromImgTag(Element imgTagElement){
		try{
			return new URL(imgTagElement.attr("src"));
		} catch(Exception e){}
		return null;
	}
	
	private static String extractTitleFromImgTag(Element imgTagElement){
		try{
			return imgTagElement.attr("alt");
		} catch(Exception e){}
		return "";
	}
	
	private static int extractWidthFromImgTag(Element imgTagElement){
		try{
			return Integer.parseInt(imgTagElement.attr("width"));
		} catch(Exception e){}
		return 0;
	}
	
	private static int extractHeightFromImgTag(Element imgTagElement){
		try{
			return Integer.parseInt(imgTagElement.attr("height"));
		} catch(Exception e){}
		return 0;
	}
}
