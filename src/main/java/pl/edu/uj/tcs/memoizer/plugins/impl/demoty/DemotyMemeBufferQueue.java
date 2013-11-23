package pl.edu.uj.tcs.memoizer.plugins.impl.demoty;

import java.util.Map;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.Meme;
import pl.edu.uj.tcs.memoizer.plugins.impl.MemeBuffer;
import pl.edu.uj.tcs.memoizer.serialization.StateObject;

public class DemotyMemeBufferQueue extends MemeBuffer {
	
	private static EViewType viewType = EViewType.QUEUE;

	public DemotyMemeBufferQueue(StateObject state){
		super(state);
	}
	
	@Override
	protected Iterable<Meme> downloadMemes(){
		return DemotyDownloader.downloadMemesFromPage(
				DemotyUrlFactory.getQueuePageUrl(1),
				viewType);
	}
}