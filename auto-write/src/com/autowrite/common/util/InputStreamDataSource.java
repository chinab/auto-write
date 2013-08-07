package com.autowrite.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class InputStreamDataSource implements DataSource {

	private byte bytes[];
	String contentType = null;

	public InputStreamDataSource(InputStream in) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte bytes[] = new byte[1024];
			int len;
			while ((len = in.read(bytes, 0, bytes.length)) != -1)
				out.write(bytes, 0, len);
			out.close();
			this.bytes = out.toByteArray();
		} catch (Exception ex) {

		}
	}

	public InputStreamDataSource(InputStream in, String contentType) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte bytes[] = new byte[1024];
			int len;
			while ((len = in.read(bytes, 0, bytes.length)) != -1)
				out.write(bytes, 0, len);
			out.close();
			this.bytes = out.toByteArray();
			this.contentType = contentType;
		} catch (Exception ex) {

		}
	}

	public InputStreamDataSource(byte bytes[]) {
		this.bytes = bytes;
	}

	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(bytes);
	}

	public OutputStream getOutputStream() throws IOException {
		return null;
	}

	public String getContentType() {
		return this.contentType;
	}

	public String getName() {
		return "";
	}
}
