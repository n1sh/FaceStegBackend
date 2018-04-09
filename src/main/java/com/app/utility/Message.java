package com.app.utility;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import javax.imageio.ImageIO;

public class Message {
	
	private int width;
    private int height;
    private boolean hasAlphaChannel;
    private int pixelLength;
    public byte[] pixels;

    public Message(String path) throws IOException
    {
        BufferedImage image = ImageIO.read(new File(path));
        pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        width = image.getWidth();
        height = image.getHeight();
        hasAlphaChannel = image.getAlphaRaster() != null;
        pixelLength = 3;
        if (hasAlphaChannel)
        {
            pixelLength = 4;
        }
    }
    public String getMessage() {
    	String message= new String();
    	String binaryMessage= new String();
        for(int i=1; i<5000; i+=pixelLength) {
        	//int a = pixels[i] & 1;
        	int b=-1;
        	if(BigInteger.valueOf(pixels[i+1]).testBit(3) == true)
        		b = pixels[i+1] & 1;
        	else
        		b = pixels[i+1]>>1  & 1;
        	//int c = pixels[i+2] & 1;
        	if(BigInteger.valueOf(pixels[i+1]).testBit(2) == true)
        		binaryMessage = binaryMessage + new Integer(b).toString();// + new Integer(b).toString() + new Integer(a).toString();
        }
        
        for(int i=0; i<binaryMessage.length()-9; i+=8) {
        	if(binaryMessage.substring(i,i+8).equals("00000000"))
        		break;
        	message += Character.toString((char)Integer.parseInt(binaryMessage.substring(i,i+8),2));
        }
    	return message;
    }
}
