package com.autowrite.common.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class Thumbnailer {
	/**
     * 주어진 이미지 파일에 대한 섬네일 파일을 생성한다
     * @param imageFile           이미지파일 전체경로(파일명 포함)
     * @param thumbnailPath       섬네일파일 전체경로(파일명 없음)
     * @param fixW                섬네일파일 너비
     * @param fixH                섬네일파일 높이
     */
    private static int    fixW     = 95;
    private static int    fixH     = 120;
    private String spec;
    private String noImagePath; 
    
    private String getType(String s){
        int i = s.lastIndexOf(".");
        if (i > 0 || i < s.length() - 1)
            return getMime(s.substring(i + 1));
        else
            return "application/octet-stream";
    }

    private String getMime(String s){
        String s1 = s.toUpperCase();
        if (s1.equals("GIF"))
            return "image/gif";
        if (s1.equals("JPG") || s1.equals("JPEG") || s1.equals("JPE"))
            return "image/jpeg";
        if (s1.startsWith("TIF"))
            return "image/tiff";
        if (s1.startsWith("PNG"))
            return "image/png";
        if (s1.equals("IEF"))
            return "image/ief";
        if (s1.equals("BMP"))
            return "image/bmp";
        if (s1.equals("RAS"))
            return "image/x-cmu-raster";
        if (s1.equals("PNM"))
            return "image/x-portable-anymap";
        if (s1.equals("PBM"))
            return "image/x-portable-bitmap";
        if (s1.equals("PGM"))
            return "image/x-portable-graymap";
        if (s1.equals("PPM"))
            return "image/x-portable-pixmap";
        if (s1.equals("RGB"))
            return "image/x-rgb";
        if (s1.equals("XBM"))
            return "image/x-xbitmap";
        if (s1.equals("XPM"))
            return "image/x-xpixmap";
        if (s1.equals("XWD"))
            return "image/x-xwindowdump";
        else
            return "application/octet-stream";
    }

    private void dumpFile(String s, OutputStream outputstream){
        String s1 = s;
        byte abyte0[] = new byte[4096];
        try {
            BufferedInputStream bufferedinputstream = new BufferedInputStream(new FileInputStream(lookupFile(s1)));
            int i;
            while ((i = bufferedinputstream.read(abyte0, 0, 4096)) != -1)
                outputstream.write(abyte0, 0, i);
            bufferedinputstream.close();
        } catch (Exception exception) {
        }
    }

    private File lookupFile(String s){
        
        File file = new File(s);
        if (file.exists() && file.isFile()) {
            
        } else {
            file = new File(noImagePath);
        }

        return file;
    }
    
    public void makeFixImage(String imageFile, String thumbnailPath, String fn) {
        try {
            imageFile     = imageFile + fn;
            Image  img  = null;
            int    imgW = 0;
            int    imgH = 0;
            
            // 파일을 생성할지 여부 확인
            if (chkCreateThumbnail(imageFile, thumbnailPath + mkName(fn, "_thumb"))) {
            
                // 모바일 사진에 "파일명+_chn"으로 카피하기
                copyfile(imageFile, thumbnailPath + mkName(fn, "_chn"));
                
                // 원본 이미지 크기 구하기
                img  = new ImageIcon(thumbnailPath + mkName(fn, "_chn")).getImage();
                imgW = img.getWidth (null);
                imgH = img.getHeight(null);
                System.out.print(" (" + imgW + "," + imgH + ")");
                
                // 원본 이미지 제단하기 (원본, 컷팅본, 원본가로, 원본세로)
                cutImage(thumbnailPath + mkName(fn, "_chn"),
                         thumbnailPath + mkName(fn, "_cut"),
                         imgW, imgH);
                
                // 떰네일 만들기
                createImageThumbnail(
                        thumbnailPath + mkName(fn, "_cut"  ),
                        thumbnailPath + mkName(fn, "_thumb"),
                        fixW, fixH);
                
                // 변환파일,커팅파일 삭제
                deleteCuttingImageFile(thumbnailPath + mkName(fn, "_chn"));
                deleteCuttingImageFile(thumbnailPath + mkName(fn, "_cut"));
            }
        } catch (Exception e) {
            System.out.println("Exception : makeFixImage()");
            e.printStackTrace();
        }
    }
    
    public void copyfile(String srFile, String dtFile){
        try {
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            InputStream in = new FileInputStream(f1);
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " in the specified directory.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean chkCreateThumbnail(String orgImage, String thumImage) {
        File orgFile  = new File(orgImage);
        File thumFile = new File(thumImage);
        if (orgFile.lastModified() > thumFile.lastModified()) {
            System.out.print("[변환대상]");
            return true;
        } else {
            System.out.print("[대상제외]" + thumImage + "\n");
            return false;
        }
    }
    
    public int [] cutImage(String orgImageFilePath, String cutImageFilePath, int imgW, int imgH) {

        File f = null;
        int[] tmpWH = {0, 0};
        try {
            f = new File(cutImageFilePath);
            Image in = null;
            tmpWH[0] = imgW;
            tmpWH[1] = imgH;
            // 01. 가로가  길때 : 가로를 조정
            System.out.println("#######" + orgImageFilePath);
            if (Float.parseFloat(String.valueOf(fixW))/Float.parseFloat(String.valueOf(fixH)) < (Float.parseFloat(String.valueOf(imgW))/Float.parseFloat(String.valueOf(imgH)))) {
                tmpWH[0] = (fixW*imgH)/fixH;
                System.out.print(" -> 가로줄임(" + tmpWH[0] + "," + tmpWH[1] + ")");
                in = ImageIO.read(new File(orgImageFilePath)).getSubimage(imgW/2-tmpWH[0]/2, 0, imgW/2+tmpWH[0]/2, imgH);
                System.out.print(":(" + (imgW/2-tmpWH[0]/2) + ",0," + (imgW/2+tmpWH[0]/2) + "," + imgH + ")");
            // 02.  세로가 길때 : 세로를 조정
            } else if (Float.parseFloat(String.valueOf(fixW))/Float.parseFloat(String.valueOf(fixH)) > (Float.parseFloat(String.valueOf(imgW))/Float.parseFloat(String.valueOf(imgH)))) {
                tmpWH[1] = (fixH*imgW)/fixW;
                System.out.print(" -> 세로줄임(" + tmpWH[0] + "," + tmpWH[1] + ")");
                in = ImageIO.read(new File(orgImageFilePath)).getSubimage(0, imgH/2-tmpWH[1]/2, imgW, imgH/2+tmpWH[1]/2);
                System.out.print(":(0," + (imgH/2-tmpWH[1]/2) + "," + imgW + "," + (imgH/2+tmpWH[1]/2) +")");
            } else {
                System.out.print(" -> 원본유지(" + tmpWH[0] + "," + tmpWH[1] + ")");
                in = ImageIO.read(new File(orgImageFilePath)).getSubimage(0, 0, imgW, imgH);
            }
            BufferedImage out = new BufferedImage(tmpWH[0], tmpWH[1], BufferedImage.TYPE_INT_RGB);
            out.getGraphics().drawImage(in, 0, 0, null);
            ImageIO.write(out, FilenameUtils.getExtension(cutImageFilePath), f);
            
        } catch (IOException e) {
            System.out.println("Exception : cutImage()");
            e.printStackTrace();
        }
        return tmpWH;
    }
    
    public void createImageThumbnail(String imagePath, String thumbnailPath, int width, int height) {
    	File f = null;
        try {
            f = new File(thumbnailPath);
            // 임시 파일 생성
//            System.out.println(" -> 최종변환(" + width + "," + height + ") " + f.getName());
            FileUtils.writeStringToFile(f, "", "EUC-KR");
            Image in = ImageIO.read(new File(imagePath)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            out.getGraphics().drawImage(in, 0, 0, null);
            ImageIO.write(out, FilenameUtils.getExtension(thumbnailPath), f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteCuttingImageFile(String cutImageFilePath) {
        File f = new File(cutImageFilePath);
        f.delete();
    }
    
    public String mkName(String fileName, String type) {
        return fileName.substring(0, fileName.length() - 4) + type + fileName.substring(fileName.length() - 4, fileName.length());
    }


}
