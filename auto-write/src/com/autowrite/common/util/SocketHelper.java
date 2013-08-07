package com.autowrite.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketHelper {

	int startInt = 998;
	int DATASEGMENT = 1024;
	Socket socket = null;
	DataInputStream dataIn = null;
	DataOutputStream dataOut = null;

	public SocketHelper(Socket soc) {
		this.socket = soc;
		// dataIn =new DataInputStream(soc.getInputStream());
	}

	public void close() {
		try {
			if (this.dataIn != null)
				this.dataIn.close();
			if (this.dataOut != null)
				this.dataOut.close();
			if (this.socket != null)
				this.socket.close();

		} catch (Exception ee) {
		}
	}

	public void setSoTimeOut(int tim) throws Exception {
		this.socket.setSoTimeout(tim);
	}

	public void sendObject(Object obj) throws Exception {
		// TODO Auto-generated method stub
		if (this.socket == null || this.socket.isClosed()) {
			throw new Exception("Socket closed");
		}
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(bao);
		objOut.writeObject(obj);
		// outputStream.close();
		objOut.close();
		byte[] data = bao.toByteArray();
		dataOut = new DataOutputStream(this.socket.getOutputStream());
		dataOut.writeInt(this.startInt);
		dataOut.writeInt(data.length);
		dataOut.write(data, 0, data.length);
		dataOut.flush();
		// dataOut.close();

	}

	public Object readObject() throws Exception {
		// TODO Auto-generated method stub

		int iread = 0;
		dataIn = new DataInputStream(this.socket.getInputStream());
		int flag = dataIn.readInt();
		if (flag != this.startInt) {
			System.out.println("this socket request is wrong");
			return null;
		}
		int dataSize = dataIn.readInt();
		/*
		 * if (dataSize > (Runtime.getRuntime().freeMemory()*0.8)){
		 * System.out.println
		 * ("InputData is too large ["+dataSize+"]. it is ignored"); return
		 * null; }
		 */
		byte[] abytes = new byte[dataSize];
		dataIn.readFully(abytes);
		// dataIn.close();

		ObjectInputStream objIn = new ObjectInputStream(
				new ByteArrayInputStream(abytes));
		Object obj = objIn.readObject();
		// inputStream.close();
		objIn.close();
		return obj;
	}

	public int send(byte[] data) throws Exception {
		// TODO Auto-generated method stub
		if (this.socket == null || this.socket.isClosed()) {
			throw new Exception("Socket closed");
		}

		dataOut = new DataOutputStream(this.socket.getOutputStream());

		dataOut.write(data, 0, data.length);
		dataOut.flush();
		return data.length;
	}

	public byte[] read() throws Exception {
		int size = -1;
		if (this.socket == null || this.socket.isClosed()) {
			throw new Exception("Socket closed");
		}
		dataIn = new DataInputStream(this.socket.getInputStream());
		int timOut = this.socket.getSoTimeout();
		int nloop = timOut / 10;
		if (nloop == 0)
			nloop = 1000;
		for (int i = 0; i < nloop; i++) {
			size = dataIn.available();
			if (size < 1) {
				Thread.sleep(10L);
			} else {
				break;
			}
		}
		return read(size);
	}

	public byte[] read(int length) throws Exception {
		try {
			byte[] temp = null;
			ByteArrayOutputStream byteOut = null;
			int size = 0;
			int readSize = 0;
			if (this.socket == null || this.dataIn == null) {
				throw new Exception("Socket is null or OutputStream was closed");
			}
			long start = System.currentTimeMillis();
			long end = -1L;
			if (byteOut == null)
				byteOut = new ByteArrayOutputStream();
			while (length > 0) {
				temp = new byte[length];
				size = dataIn.read(temp, 0, length);
				end = System.currentTimeMillis();
				if (size != -1 && size < length) {
					byteOut.write(temp, 0, size);
					Thread.sleep(10L);
					start = end;
				} else if (size == -1) {
					if (end - start < 6000L) {
						Thread.sleep(10L);
						continue;
					} else {
						return byteOut.toByteArray();
					}
				} else {
					byteOut.write(temp, 0, size);
					break;
				}
				length = length - size;
			}
			return byteOut.toByteArray();
		} catch (Exception e) {
			throw e;
		}

	}
}
