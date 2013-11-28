package pl.edu.uj.tcs.memoizer.plugins.impl.demoty;

import pl.edu.uj.tcs.memoizer.plugins.EViewType;
import pl.edu.uj.tcs.memoizer.plugins.IDownloadPlugin;
import pl.edu.uj.tcs.memoizer.plugins.IPluginFactory;
import pl.edu.uj.tcs.memoizer.plugins.InvalidViewException;
import pl.edu.uj.tcs.memoizer.serialization.StateObject;
import pl.edu.uj.tcs.memoizer.plugins.impl.demoty.DemotySequentialDownloader;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/*
 * Demotywatory.pl download plugin factory implementation
 * @author pmikos (sokar92)
 */
public class DemotyDownloadPluginFactory implements IPluginFactory {

	@Override
	public List<EViewType> getAvailableDownloadViews() {
		List<EViewType> list = new ArrayList<EViewType>();
		list.add(EViewType.CHRONOLOGICAL);
		list.add(EViewType.FAVOURITE);
		//list.add(EViewType.UNSEEN);
		list.add(EViewType.QUEUE);
		return list;
	}

	@Override
	public Image getIcon() {
		return null;
	}

	@Override
	public String getServiceName() {
		return "Demotywatory";
	}

	@Override
	public IDownloadPlugin newInstance(StateObject state, EViewType view)
			throws InvalidViewException {
		switch(view){
		case CHRONOLOGICAL:
			return new DemotySequentialDownloader("DemotyChrono", state, view, "http://www.demotywatory.pl/page");
		case QUEUE:
			return new DemotySequentialDownloader("DemotyQueue", state, view, "http://www.demotywatory.pl/poczekalnia/page");
		case FAVOURITE:
			return new DemotySequentialDownloader("DemotyTopPercent", state, view, "http://www.demotywatory.pl/topka/procenty/page");
		//case UNSEEN:
			//return null;
		default:
			throw new InvalidViewException();	
		}
	}
	
}