package pl.edu.uj.tcs.memoizer.plugins.impl.demoty;

import java.util.Map;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.Meme;
import pl.edu.uj.tcs.memoizer.plugins.impl.MemeBuffer;
import pl.edu.uj.tcs.memoizer.serialization.StateObject;

public class DemotyMemeBufferUnseen extends MemeBuffer {
	
	private static EViewType viewType = EViewType.UNSEEN;

	public DemotyMemeBufferUnseen(StateObject state){
		super(state);
	}
	
	@Override
	protected Iterable<Meme> downloadMemes(){
		return null;
	}
}