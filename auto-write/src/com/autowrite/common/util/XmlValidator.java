package com.autowrite.common.util;

import java.io.ByteArrayInputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XmlValidator extends DefaultHandler {
	public boolean validate(String xmlfile) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		factory.setFeature("http://apache.org/xml/features/validation/schema", true);
		SAXParser parser = factory.newSAXParser();

		parser.parse(xmlfile, new XmlValidator());
		// XMLReader reader =parser.getXMLReader();

		// reader.parse(xmlfile);
		return true;
	}

	public void error(org.xml.sax.SAXParseException ex)
			throws org.xml.sax.SAXException {
		System.out.println("XML Invalid [" + ex.getLineNumber() + ","
				+ ex.getColumnNumber() + "]:" + ex.getMessage());
		throw ex;
	}

	public void fatalError(org.xml.sax.SAXParseException ex)
			throws org.xml.sax.SAXException {
		System.out.println("Well formed error");
		throw ex;
	}

	public boolean validateSchema(String xmlfile, Source[] srcXsd)
			throws Exception {
		SchemaFactory schFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
		Schema schema = schFactory.newSchema(srcXsd);
		Validator validator = schema.newValidator();
		validator.setErrorHandler(new XmlValidator());
		validator.validate(new StreamSource(xmlfile));

		return true;
	}

	public boolean validateSchema(byte[] xmldoc, Source[] srcXsd)
			throws Exception {
		SchemaFactory schFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
		Schema schema = schFactory.newSchema(srcXsd);
		Validator validator = schema.newValidator();
		validator.setErrorHandler(new XmlValidator());
		validator.validate(new StreamSource(new ByteArrayInputStream(xmldoc)));

		return true;
	}

	public boolean validateSchema(String xmlfile, String xsdfile)
			throws Exception {
		Source srcXsd = new StreamSource(xsdfile);
		return this.validateSchema(xmlfile, new Source[] { srcXsd });

	}
}
