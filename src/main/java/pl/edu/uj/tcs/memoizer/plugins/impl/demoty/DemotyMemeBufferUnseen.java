package pl.edu.uj.tcs.memoizer.plugins.impl.demoty;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.Meme;
import pl.edu.uj.tcs.memoizer.plugins.impl.MemeBuffer;
import pl.edu.uj.tcs.memoizer.serialization.StateObject;

public class DemotyMemeBufferUnseen extends MemeBuffer {
	
	private static EViewType viewType = EViewType.UNSEEN;

	private URL lastSeenLink;
	
	public DemotyMemeBufferUnseen(StateObject state){
		super(state);
		lastSeenLink = null;
	}
	
	@Override
	protected Iterable<Meme> downloadMemes(){
		if(lastSeenLink == null)
			return downloadFirstTime();
		return download();
	}
	
	private Iterable<Meme> downloadFirstTime(){
		List<Meme> result = new ArrayList<Meme>();
		
		for(int i=10;i>0;--i){
			List<Meme> got = makeList(getMemesFromPage(i));
			for(int j=got.size()-1;j>=0;--j)
				result.add(got.get(j));
		}
		
		lastSeenLink = result.get(result.size()-1).getImageLink();
		
		return result;
	}
	
	private Iterable<Meme> download(){
		List<Meme> result = new ArrayList<Meme>();
		
		boolean found = false;
		int count = 1, ind;
		while(!found && count <= 20){
			List<Meme> memes = makeList(getMemesFromPage(count));
			
			ind = 0;
			for(int i=0;i<memes.size();++i){
				if(lastSeenLink.equals(memes.get(i).getImageLink())){
					ind = i;
					found = true;
					break;
				}
			}
				
			if(found) {
				for(int j=ind-1;j>=0;--j)
					result.add(memes.get(j));
				break;
			}
			count++;
		}
		
		while(--count > 0){
			List<Meme> got = makeList(getMemesFromPage(count));
			for(int i=got.size()-1;i>=0;--i)
				result.add(got.get(i));
		}
		
		lastSeenLink = result.get(result.size()-1).getImageLink();
		
		return result;
	}
	
	private Iterable<Meme> getMemesFromPage(int num){
		return DemotyDownloader.downloadMemesFromPage(
				DemotyUrlFactory.getMainPageUrl(num),
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