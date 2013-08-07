package com.autowrite.common.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.*;

public class CopyOfThumbnailer {
	private Image inImage;
	private String thumb;
	
	private int maxX;
	private int maxY;
	private int margin;
	
	public CopyOfThumbnailer(){
		
	}
	
	public CopyOfThumbnailer(String origin, String thumb, int xx, int yy){
		initLocal(origin, thumb, xx, yy, 0);
	}
	
	public CopyOfThumbnailer(String origin, String thumb, int xx, int yy, int margin){
		initLocal(origin, thumb, xx, yy, margin);
	}
	
	public CopyOfThumbnailer(URL remotePath, String thumb, int xx, int yy){
		initRemote(remotePath, thumb, xx, yy, 0);
	}
	
	public CopyOfThumbnailer(URL remotePath, String thumb, int xx, int yy, int margin){
		initRemote(remotePath, thumb, xx, yy, margin);
	}
	
	private void initLocal(String origin, String thumb, int xx, int yy, int margin) {
		this.inImage = new ImageIcon(origin).getImage();
		this.thumb = thumb;
		this.maxX = xx;
		this.maxY = yy;
		this.margin = margin;
	}

	private void initRemote(URL remotePath, String thumb, int xx, int yy, int margin) {
		this.inImage = new ImageIcon(remotePath).getImage();
		this.thumb = thumb;
		this.maxX = xx;
		this.maxY = yy;
		this.margin = margin;
	}
	
	public void createThumbnail(){
		try {
			long stime = System.currentTimeMillis();
			// get the image from file;
			long midtime = System.currentTimeMillis();
			
			// determine the scale
			double scaleX = (double)(maxX - margin*2)/inImage.getWidth(null);
			double scaleY = (double)(maxY - margin*2)/inImage.getHeight(null);
			double scale = scaleX;
			
			if ( scaleX > scaleY ){
				scale = scaleY;
			}
			
			// determine size of new image
			int scaledW = (int)(scale*inImage.getWidth(null));
			int scaledY = (int)(scale*inImage.getHeight(null));
			
			// create image buffer 
			BufferedImage outImage = new BufferedImage(maxX, maxY, BufferedImage.TYPE_INT_RGB);
			
			// set the scale
			AffineTransform tx = new AffineTransform();
			
			// if the image size is smaller than expected, don't bother scaling.
			// if ( scale < 1.0d ) {
			tx.scale(scale, scale);
			// }
			
			AffineTransform toCenterAt = new AffineTransform();
			int startX = (maxX - scaledW)/2;
			int startY = (maxY - scaledY)/2;
			toCenterAt.translate(startX, startY);
			toCenterAt.concatenate(tx);
			
			// paint image
			Graphics2D g2d = outImage.createGraphics();
			RenderingHints qualityHints;
			qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHints(qualityHints);
			g2d.fillRect(0, 0, maxX, maxY);
			g2d.drawImage(inImage, toCenterAt, null);
			g2d.dispose();
			
			// encode image to JPEG-encode and write to the file.
			OutputStream os = new FileOutputStream(thumb);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
			encoder.encode(outImage);
			os.close();
			
			
			
		} catch ( IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
