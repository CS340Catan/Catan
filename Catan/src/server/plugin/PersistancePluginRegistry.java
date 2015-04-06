package server.plugin;

import java.util.ArrayList;

import server.persistance.IPersistance;

public class PersistancePluginRegistry {
	/**
	 * Private member: registeredPlugins list
	 */
	private ArrayList<PluginDescription> registeredPlugins = null;
	
	/**
	 * given a PluginDescription,
	 * make the correct folders
	 */
	public void registerPlugin(PluginDescription pluginDescription) {
		
	}
	
	/**
	 * If registeredPlugins = null:
	 *   Search through plugin folder for files called "description.txt"
	 *   add their information to a new PluginDescription object
	 *   keep searching
	 *   set registeredPlugins 
	 * return the list
	 */
	public ArrayList<PluginDescription> getAvailablePlugins() {
		
		return registeredPlugins;
	}
	
	/**
	 * using path field in PluginDescription locate file "description.txt"
	 * delete it
	 */
	public void UnregisterPlugin(PluginDescription pluginDescription) {
		
	}
	
	/** URLClassloader sets plugin JAR file on its classpath
	 *  uses Class.forName() to load the class
	 *  uses Class.newInstance() to create the class
	 *  cast the class to an IPersistance
	 *  return the IPersistance
	 */
	public IPersistance getPlugin(PluginDescription pluginDescription) {
		
		return null;
	}
}
