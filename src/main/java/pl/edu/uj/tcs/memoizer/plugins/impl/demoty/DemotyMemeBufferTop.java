package pl.edu.uj.tcs.memoizer.plugins.impl.demoty;

import pl.edu.uj.tcs.memoizer.plugins.Meme;
import pl.edu.uj.tcs.memoizer.plugins.impl.MemeBuffer;
import java.util.Map;

public class DemotyMemeBufferTop extends MemeBuffer {
	
	public DemotyMemeBufferTop(Map<String, Object> state){
		super(state);
	}
	
	@Override
	protected Iterable<Meme> downloadMemes(){
		return DemotyDownloader.downloadMemesFromPage(
				DemotyUrlFactory.getTopByPercentPageUrl(1)
				);
	}
}