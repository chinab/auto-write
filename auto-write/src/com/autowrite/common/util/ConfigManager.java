package com.autowrite.common.util;

import com.ibatis.common.resources.Resources;
import java.util.List;
import org.apache.commons.configuration.*;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class ConfigManager {

	public static ConfigManager getInstance() {
		if (instance == null)
			instance = new ConfigManager();
		return instance;
	}

	private ConfigManager() {
		configPath = "config/config.xml";
		allConfig = new CompositeConfiguration();
		try {
			loadConfig();
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	public void loadConfig() throws Exception {
		java.io.File f = Resources.getResourceAsFile(configPath);
		java.net.URL curl = getClass().getResource(configPath);
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

	String configPath;
	CompositeConfiguration allConfig;
	static ConfigManager instance = null;

}
