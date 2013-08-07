package com.autowrite.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.activation.DataSource;
import javax.activation.FileTypeMap;

public class ByteArrayDataSource implements DataSource, Serializable {
	private static final long serialVersionUID = 9179194301410526578L;

	byte[] bytes = null;
	// OutputStream baos =null;
	String contentType = null;
	String soureName = null;

	public ByteArrayDataSource(byte[] bytes, String contentType) {
		this.bytes = bytes;
		this.contentType = contentType;
		// baos =new ByteArrayOutputStream();
	}

	public InputStream getInputStream() throws IOException {

		// if (bytes==null) bytes =baos.();
		ByteArrayInputStream bai = new ByteArrayInputStream(bytes);

		return bai;
	}

	public OutputStream getOutputStream() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// baos.write(bytes);
		return baos;
	}

	public String getContentType() {
		return this.contentType;
	}

	public String getName() {
		return soureName;
	}

	public void setName(String s) {
		soureName = s;
	}
}
