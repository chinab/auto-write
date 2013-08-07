package com.autowrite.common.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XsltHelper {
	Map outputProperty = null;
	Map parameter = new HashMap();

	public void setOutputProperty(Map m) {
		this.outputProperty = m;
	}

	public void setParameter(String arg, Object obj) {
		this.parameter.put(arg, obj);
	}

	protected void setOutputProperty(Transformer transformer) {
		if (this.outputProperty != null) {
			Iterator ite = outputProperty.keySet().iterator();
			String skey = null;
			while (ite.hasNext()) {
				skey = (String) ite.next();
				transformer.setOutputProperty(skey,
						(String) outputProperty.get(skey));
			}
		} else
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	}

	protected void setParameters(Transformer transformer) {
		if (this.parameter != null) {
			Iterator ite = parameter.keySet().iterator();
			String skey = null;
			while (ite.hasNext()) {
				skey = (String) ite.next();
				transformer.setParameter(skey, parameter.get(skey));
			}
		}

	}

	public void doTransform(String srcXml, String xslfile, Result result)
			throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(
				xslfile));
		this.setOutputProperty(transformer);
		setParameters(transformer);
		Source sxml = new StreamSource(srcXml);

		transformer.transform(sxml, result);

	}

	public byte[] doTransform(String srcXml, String xslfile) throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(
				xslfile));
		this.setOutputProperty(transformer);
		setParameters(transformer);
		Source sxml = new StreamSource(srcXml);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		Result result = new StreamResult(bao);
		transformer.transform(sxml, result);
		bao.close();
		byte[] abytes = bao.toByteArray();

		return abytes;

	}

	public byte[] doTransform(InputStream in, String xslfile) throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(
				xslfile));
		this.setOutputProperty(transformer);
		setParameters(transformer);
		Source sxml = new StreamSource(in);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		Result result = new StreamResult(bao);
		transformer.transform(sxml, result);
		bao.close();
		byte[] abytes = bao.toByteArray();

		return abytes;
	}

	public void doTransform(Source xmlSource, Source xslSource, Result result)
			throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xslSource);
		this.setOutputProperty(transformer);
		setParameters(transformer);
		transformer.transform(xmlSource, result);
	}

	public byte[] doTransform(Source xmlSource, Source xslSource)
			throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xslSource);
		this.setOutputProperty(transformer);
		setParameters(transformer);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		Result result = new StreamResult(bao);
		transformer.transform(xmlSource, result);

		bao.close();
		byte[] abytes = bao.toByteArray();

		return abytes;
	}
}
