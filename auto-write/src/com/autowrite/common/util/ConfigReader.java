package com.autowrite.common.util;

import com.ibatis.common.resources.Resources;
import java.io.File;
import java.io.PrintStream;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class ConfigReader
{

    public ConfigReader()
    {
        configs = null;
        configStore = new HashMap();
    }

    public void setConfigs(Map m)
    {
        configs = m;
        loadConfig();
    }

    private void loadConfig()
    {
        Iterator ite = configs.keySet().iterator();
        String skey = null;
        String val = null;
        for(; ite.hasNext(); System.out.println((new StringBuilder(String.valueOf(skey))).append(":").append(val).toString()))
        {
            skey = (String)ite.next();
            val = (String)configs.get(skey);
            Map m;
            if(val.startsWith("file:"))
                m = readResource(val.substring(5));
            else
                configStore.put(skey, val);
        }

    }

    private Map readResource(String res)
    {
        String skey = null;
        String val = null;
        HashMap m = new HashMap();
        System.out.println((new StringBuilder("config file :")).append(res).toString());
        try
        {
            File f = Resources.getResourceAsFile(res);
            Document doc = loadXML(f);
            List al = getChildren(doc.getDocumentElement());
            Element e = null;
            for(int i = 0; i < al.size(); i++)
            {
                e = (Element)al.get(i);
                skey = e.getAttribute("name");
                val = e.getAttribute("value");
                if(skey != null && val != null)
                    m.put(skey, val);
            }

            return m;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    private List getChildren(Element documentElement)
    {
        NodeList nl = documentElement.getChildNodes();
        ArrayList al = new ArrayList();
        for(int i = 0; i < nl.getLength(); i++)
            al.add(nl.item(i));

        return al;
    }

    public String get(String skey)
    {
        String tmpKey = skey;
        String sndKey = null;
        String retVal = null;
        int ipos = skey.indexOf(".");
        if(ipos > 0)
        {
            tmpKey = skey.substring(0, ipos);
            sndKey = skey.substring(ipos + 1);
            Map m = (Map)configStore.get(tmpKey);
            if(m == null)
                return null;
            retVal = (String)m.get(sndKey);
        } else
        {
            retVal = (String)configStore.get(skey);
        }
        return retVal;
    }

    public Map getMap(String skey)
    {
        Map m = (Map)configStore.get(skey);
        return m;
    }

    public Document loadXML(File f)
        throws Exception
    {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(f);
        return document;
    }

    Map configs;
    HashMap configStore;
}
