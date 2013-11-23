package pl.edu.uj.tcs.memoizer.plugins.impl.demoty;

import pl.edu.uj.tcs.memoizer.plugins.IPlugin;
import pl.edu.uj.tcs.memoizer.plugins.IPluginFactory;
import pl.edu.uj.tcs.memoizer.serialization.StateObject;
import java.awt.Image;
import java.util.Map;

/*
 * Demotywatory.pl download plugin factory implementation
 * @author pmikos (sokar92)
 */
public class DemotyDownloadPluginFactory implements IPluginFactory {
	
	public String getPluginName(){
		return "Demotywatory";
	}
	
	public Image getIcon(){
		return null;
	}
	
	public IPlugin newInstance(StateObject pluginState){
		return new DemotyDownloadPlugin(pluginState);
	}
	
}