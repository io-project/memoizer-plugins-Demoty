package pl.edu.uj.tcs.memoizer.plugins.impl.demoty;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.Meme;
import pl.edu.uj.tcs.memoizer.plugins.impl.MemeBuffer;
import pl.edu.uj.tcs.memoizer.serialization.StateObject;

import java.util.Map;

public class DemotyMemeBufferChrono extends MemeBuffer {

	private static EViewType viewType = EViewType.CHRONOLOGICAL;
	
	public DemotyMemeBufferChrono(StateObject state){
		super(state);
	}
	
	@Override
	protected Iterable<Meme> downloadMemes(){
		return DemotyDownloader.downloadMemesFromPage(
				DemotyUrlFactory.getMainPageUrl(1),
				viewType);
	}
}