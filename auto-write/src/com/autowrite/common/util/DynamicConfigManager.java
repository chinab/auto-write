package com.autowrite.common.util;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

import com.ibatis.common.resources.Resources;

public class DynamicConfigManager {
	String configPath = "config/config.xml";
	CompositeConfiguration allConfig = new CompositeConfiguration();;

	static DynamicConfigManager instance = null;

	public static DynamicConfigManager getInstance() {

		if (instance == null)
			instance = new DynamicConfigManager();
		return instance;
	}

	private DynamicConfigManager() {

	}

	public boolean loadConfig(String configFile) throws Exception {
		File f = new File(configFile);
		if (!f.exists() || !f.getName().toLowerCase().endsWith(".xml"))
			return false;
		try {
			XMLConfiguration config = new XMLConfiguration(f);

			config.setReloadingStrategy(new FileChangedReloadingStrategy());
			allConfig.addConfiguration(config);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return true;
	}

	public void loadConfig() throws Exception {
		File f = Resources.getResourceAsFile(configPath);

		URL curl = getClass().getResource(configPath);
		XMLConfiguration config = new XMLConfiguration(f);
		PropertiesConfiguration pc = null;
		List al = config.getList("config");
		String cfile = null;
		for (int i = 0; i < al.size(); i++) {
			cfile = (String) al.get(i);
			f = Resources.getResourceAsFile(cfile);
			if (cfile.toLowerCase().endsWith(".xml")) {

				config = new XMLConfiguration(f);

				config.setReloadingStrategy(new FileChangedReloadingStrategy());
				allConfig.addConfiguration(config);
			} else {
				pc = new PropertiesConfiguration(f);
				pc.setReloadingStrategy(new FileChangedReloadingStrategy());
				allConfig.addConfiguration(pc);
			}
		}

	}

	public String getString(String skey) {
		return allConfig.getString(skey);
	}

	public List getList(String skey) {
		return allConfig.getList(skey);
	}

	public int getInt(String skey) {
		return allConfig.getInt(skey);
	}

	public float getFloat(String skey) {
		return allConfig.getFloat(skey);
	}
}
