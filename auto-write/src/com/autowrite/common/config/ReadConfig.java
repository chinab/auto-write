package com.autowrite.common.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class ReadConfig {
	private static Hashtable _map = null;
	private static HashMap configFiles = new HashMap();
	private static final String _configFile = "Config.xml";
	private static boolean _debug = false; // Constant._DEBUG ;
	private static DOMParser builder = new DOMParser();
	private static String _configDir = null;

	static {
		_map = init(_configFile);
	}

	public static Hashtable init(String fileName) {
		Hashtable map = new Hashtable();

		try {
			InitialContext ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup("java:comp/env");
			_configDir = (String) envCtx.lookup("config");
		} catch (Exception ee) {
			// log.error(ee.getMessage());

		}
		try {
			// sels, asia server could not have config info from context;

			if (_configDir == null || _configDir.length() == 0) {
				_configDir = System.getProperty("APPCONFIG");
				if (_configDir == null || _configDir.length() == 0) {
					_configDir = "/user/sels/WebServer/conf/";
					File f = new File(_configDir);
					if (!f.exists()) {
						_configDir = "/export/asiaetr/WebServer/conf/";

					}

				}
			}

			System.out.println("Config DIr :" + _configDir);
			System.setProperty("APPCONFIG", _configDir);
			fileName = _configDir + fileName;
			File fp = new File(fileName);
			if (!fp.exists()) {
				if (_debug)
					System.out.println("no file - " + fileName);
				return null;
			}

			if (builder == null) {
				if (_debug)
					System.out.println("SAXBuilder Create Fail..");
				return null;
			}
			builder.parse(fp.toURL());
			Document doc = builder.getDocument();
			Element root = doc.getDocumentElement();
			if (_debug)
				System.out.println("Root Name : " + root.getTagName());
			NodeList children = root.getChildNodes();
			int count = children.getLength();
			String hashKey = null;
			Object hashValue = null;
			if (_debug)
				System.out.println("Config COunt :" + count);
			for (int i = 0; i < count; i++) {
				hashKey = null;
				hashValue = null;

				Node e = children.item(i);
				if (_debug)
					System.out.println("Node type :" + e.getNodeType());
				if (e.getNodeType() != Node.ELEMENT_NODE)
					continue;
				NamedNodeMap eAttributes = e.getAttributes();

				if (eAttributes.getLength() < 1) {
					if (_debug)
						System.out.println("ADD :: " + e.getNodeName() + " ==> " + e.getNodeValue());
					map.put(e.getNodeName().trim(), e.getNodeValue().trim());
				} else {
					// get attribute.

					int attCnt = eAttributes.getLength();
					for (int j = 0; j < attCnt; j++) {
						Node attribute = eAttributes.item(j);
						String attriName = attribute.getNodeName();
						String attriValue = ((Element) e).getAttribute(attriName);
						if (_debug)
							System.out.println("att name :" + attriName + "  value:" + attriValue);
						if (attriName.trim().equalsIgnoreCase("name")) {
							hashKey = attriValue.trim();
						} else if (attriName.trim().equalsIgnoreCase("value")) {
							if (attriValue.trim().endsWith(".xml")) {
								hashValue = getRoot(hashKey, attriValue);
							} else {
								hashValue = attriValue.trim();
							}
						}
						// remained attribute is ignored ??
						if (hashKey != null && hashValue != null) {
							map.put(hashKey, hashValue);
						}
					} // end of while statement
				}
			} // end of while statement
		} // end of try statement
		catch (Exception e) {
			System.out.println("Read Config Initialize failed");
			if (_debug)
				e.printStackTrace();
			// log.error(e);
			return null;
		}
		return map;
	}

	private static Node getRoot(String channel, String configName) throws Exception {
		String fileName = _configDir + configName;
		File fp = new File(fileName);
		if (!fp.exists()) {
			if (_debug)
				System.out.println("no file - " + fileName);
			return null;
		}
		Long ftime = new Long(fp.lastModified());
		ArrayList fileInfo = new ArrayList();
		fileInfo.add(configName);
		fileInfo.add(ftime);
		configFiles.put(channel, fileInfo);
		try {
			if (builder == null) {
				if (_debug)
					System.out.println("SAXBuilder Create Fail..");
				return null;
			}
			builder.parse(fp.toURL());
			Document doc = builder.getDocument();
			Element root = doc.getDocumentElement();

			return (Node) root;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	public static String[] getValue(String channel, String key) {
		Hashtable map = null;
		String value = null;
		String[] arr = null;
		try {
			if (_map == null) {
				if (_debug)
					System.out.println("No Hashtable");
				return null;
			}
			checkConfigChanged(channel);

			Object obj = _map.get(channel.trim());

			if (obj == null) {
				if (_debug)
					System.out.println("Do not exist Key [" + channel + "]");
				return null;
			}
			if (obj instanceof Node) {
				XMLNode root = (XMLNode) ((Node) obj);
				NodeList nl = root.selectNodes(key);
				if (nl == null)
					return null;
				int nCnt = nl.getLength();
				if (nCnt > 0) {
					arr = new String[nCnt];
					for (int i = 0; i < nCnt; i++) {
						arr[i] = getNodeValue(nl.item(i));
						if (_debug)
							System.out.println(nl.item(i).getNodeName() + "   " + arr[i]);
					}
				}

			} else {
				if (_debug)
					System.out.println("Invalid Hashtable... you must reset this config");
				return null;
			}
		} catch (Exception e) {
			if (_debug)
				System.out.println("ReadConfig : " + e.toString());
			return null;
		}
		return arr;
	}

	public static NodeList getNodeList(String channel, String key) {
		Hashtable map = null;
		NodeList nl = null;
		try {
			if (_map == null) {
				if (_debug)
					System.out.println("No Hashtable");
				return null;
			}
			checkConfigChanged(channel);
			Object obj = _map.get(channel.trim());

			if (obj == null) {
				if (_debug)
					System.out.println("Do not exist Key [" + channel + "]");
				return null;
			}
			if (obj instanceof Node) {
				XMLNode root = (XMLNode) ((Node) obj);
				nl = root.selectNodes(key);
			} else {
				if (_debug)
					System.out.println("Invalid Hashtable... you must reset this config");
				return null;
			}
		} catch (Exception e) {
			if (_debug)
				System.out.println("ReadConfig : " + e.toString());
			return null;
		}
		return nl;
	}

	private static String getNodeValue(Node nd) {
		if (nd != null && nd.hasChildNodes())
			return nd.getFirstChild().getNodeValue();
		else
			return "";
	}

	public static String get(String channel, String key) {
		if (_debug)
			System.out.println("channel:" + channel);
		if (_debug)
			System.out.println("key:" + key);
		// log.debug("channel:"+channel);
		// log.debug("key:"+key);
		String[] arr = getValue(channel, key);
		if (arr != null) {
			// log.debug("val :"+arr[0]);
			return arr[0];
		} else
			return "";
	}

	public static String get(String channel, String doc, String key) {
		String newPath = getPath(channel, doc, key);
		String[] arr = getValue(channel, newPath + key);
		if (arr != null) {
			return arr[0];
		} else
			return "";
	}

	public static String get(String channel, String site, String doc, String key) {
		String newPath = getPath(channel, site, doc, key);
		String[] arr = getValue(channel, newPath + key);
		if (arr != null) {
			return arr[0];
		} else
			return "";
	}

	public static String get(String channel, String app, String site, String doc, String key) {
		String newPath = getPath(channel, app, site, doc, key);
		String[] arr = getValue(channel, newPath + key);
		if (arr != null) {
			return arr[0];
		} else
			return "";
	}

	public static String getPath(String channel, String doc, String key) {
		String newPath = null;
		NodeList arr = getNodeList(channel, doc + key);
		if (arr != null && arr.getLength() > 0) {
			newPath = doc;
		} else
			newPath = "GENERAL";
		if (_debug)
			System.out.println("newPath:" + newPath);
		return newPath;
	}

	public static String getPath(String channel, String app, String doc, String key) {
		String newPath = null;
		NodeList arr = getNodeList(channel, app + "/" + doc + key);
		if (arr != null && arr.getLength() > 0) {
			newPath = app + "/" + doc;
		} else {
			arr = getNodeList(channel, doc + key);
			if (arr != null && arr.getLength() > 0) {
				newPath = doc;
			} else
				newPath = "GENERAL";
		}
		if (_debug)
			System.out.println("newPath:" + newPath);
		return newPath;
	}

	public static String getPath(String channel, String app, String site, String doc, String key) {
		String newPath = null;
		NodeList arr = getNodeList(channel, app + "/" + site + "/" + doc + key);
		if (arr != null && arr.getLength() > 0) {
			newPath = app + "/" + site + "/" + doc;
		} else {
			arr = getNodeList(channel, app + "/" + doc + key);
			if (arr != null && arr.getLength() > 0) {
				newPath = app + "/" + doc;
			} else {
				arr = getNodeList(channel, doc + key);
				if (arr != null && arr.getLength() > 0) {
					newPath = doc;
				} else
					newPath = "GENERAL";
			}
		}
		if (_debug)
			System.out.println("newPath:" + newPath);
		return newPath;
	}

	public static String[] getAll(String channel, String key) {
		return getValue(channel, key);
	}

	public static Hashtable init() {
		_map.clear();
		return init(_configFile);
	}
	public static Hashtable getMap() {
		return _map;
	}
	public static Object get(String channel) {
		Object map = null;
		String value = null;
		Object obj = null;
		try {
			if (_map == null) {
				if (_debug)
					System.out.println("No Hashtable");
				return null;
			}
			if (_debug)
				System.out.println("channel : " + channel);
			checkConfigChanged(channel);
			obj = _map.get(channel.trim());
		} catch (Exception e) {
			if (_debug)
				System.out.println("ReadConfig Exception : " + e.toString());
			return null;
		}
		return obj;
	}

	public static boolean checkConfigChanged(String channel)

	{
		boolean b_ret = false;
		try {
			List fInfo = (List) configFiles.get(channel);
			if (fInfo == null)
				return b_ret;
			String fname = (String) fInfo.get(0);
			Long ftime = (Long) fInfo.get(1);
			File f = new File(_configDir + fname);
			long newTime = f.lastModified();
			if (newTime > ftime.longValue()) {
				fInfo.set(1, new Long(newTime));
				_map.put(channel, getRoot(channel, fname));
				b_ret = true;
				System.out.println("ReadConfig :" + channel + " refreshed");
			}
		} catch (Exception ex) {
			System.out.println("ReadConfig ERROR:" + channel + ":" + ex.getMessage());
		}

		return b_ret;
	}
	public static String getConfigFile(String channel) {
		ArrayList cfile = (ArrayList) configFiles.get(channel);
		if (cfile == null)
			return null;
		String fname = (String) cfile.get(0);
		return _configDir + fname;
	}

}