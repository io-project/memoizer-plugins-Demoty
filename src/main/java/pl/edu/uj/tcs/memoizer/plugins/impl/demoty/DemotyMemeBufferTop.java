package pl.edu.uj.tcs.memoizer.plugins.impl.demoty;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.Meme;
import pl.edu.uj.tcs.memoizer.plugins.impl.MemeBuffer;
import pl.edu.uj.tcs.memoizer.serialization.StateObject;

import java.net.URL;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class DemotyMemeBufferTop extends MemeBuffer {
	
	private static EViewType viewType = EViewType.FAVOURITE;
	
	private URL lastSeenLink;
	private int lastSeenPage;
	
	public DemotyMemeBufferTop(StateObject state){
		super(state);

		lastSeenPage = 0;
		lastSeenLink = null;
	}
	
	@Override
	protected Iterable<Meme> downloadMemes(){
		if(lastSeenPage == 0 || lastSeenLink == null)
			return downloadFirstTime();
		return download();
	}
	
	private Iterable<Meme> downloadFirstTime(){
		List<Meme> lst = makeList(getMemesFromPage(1));
		
		if(lst.size() > 0){
			lastSeenPage = 1;
			lastSeenLink = lst.get(lst.size()-1).getImageLink();
		}
		
		return lst;
	}
	
	private Iterable<Meme> download(){
		List<Meme> result = new ArrayList<Meme>();
		
		boolean found = false;
		int count = 0;
		while(!found && count < 10){
			Iterable<Meme> memes = getMemesFromPage(lastSeenPage);
			
			for(Meme m : memes){
				if(lastSeenLink.equals(m.getImageLink())){
					found = true;
				} else if(found){
					result.add(m);
				}
			}
			
			if(++lastSeenPage > 10)
				lastSeenPage = 0;
			
			count++;
		}
		
		List<Meme> nextPage = makeList(getMemesFromPage(lastSeenPage));
		result.addAll(nextPage);
		
		if(result.size() > 0)
			lastSeenLink = result.get(result.size()-1).getImageLink();
		
		return result;
	}
	
	private Iterable<Meme> getMemesFromPage(int num){
		return DemotyDownloader.downloadMemesFromPage(
				DemotyUrlFactory.getTopByPercentPageUrl(num),
				viewType);
	}
	
	private List<Meme> makeList(Iterable<Meme> iter){
		List<Meme> result = new ArrayList<Meme>();
		for(Meme m : iter){
			result.add(m);
		}
		return result;
	}
}