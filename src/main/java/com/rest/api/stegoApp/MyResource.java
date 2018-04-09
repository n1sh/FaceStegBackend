package com.rest.api.stegoApp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.app.utility.FaceRecognition;
import com.app.utility.Message;
import com.facesteg.dao.ImageHandler;
import com.facesteg.dao.ImageHandlerImpl;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import sun.misc.BASE64Decoder;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/")
public class MyResource {
	
	static int counter = 0;
	String username,password,name,age,dob;
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
//    @GET
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getIt() {
//        return "Got it!";
//    }
	@POST
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
	@Path("login")
	public String uploadImage(String img) throws IOException{
		String sourceData = img;
		System.out.println(img);
		// tokenize the data
		String[] parts = sourceData.split(",");
		String imageString = parts[1];
		
		// create a buffered image
		BufferedImage image = null;
		byte[] imageByte;
		
		BASE64Decoder decoder = new BASE64Decoder();
		imageByte = decoder.decodeBuffer(imageString);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();
		
		
		String filename = incrementCounter();
		String commonpath = "E:\\ImageData\\LoginChecks\\";
		File outputfile = new File(commonpath + filename);
		ImageIO.write(image, "png", outputfile);
		
		
		try {
			Message obj = new Message(commonpath + filename);
			String respData = obj.getMessage();
			System.out.println(respData);
			int division = respData.indexOf('|');
			String username = respData.substring(0, division);
			String password = respData.substring(division+1,respData.length());
			FaceRecognition facerec = new FaceRecognition();
			System.out.println(commonpath+filename);
			int id = facerec.recognize("E:\\ImageData\\SignUpData\\", commonpath+filename);
			if(id == -1) {
				return "faceError";
			}
			//System.out.println("E:\\ImageData\\SignUpData\\" + id + "-" + username + "1.png");
			Message obj2 = new Message("E:\\ImageData\\SignUpData\\" + id + "-" + username + "1.png");
			//System.out.println("E:\\ImageData\\SignUpData\\" + id + "-" + username + "1.png");
			String[] userdata = obj2.getMessage().split("@@");
			String actualUsername = userdata[0];
			String actualPassword = userdata[1];
			if(actualUsername.equals(username) && actualPassword.equals(password))
				return "valid";
			else
				return "invalid";
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "error";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
	@Path("signup")
	public String registerUser(String img) throws IOException{
		
		String[] parts = img.split("@@");
		
		BufferedImage image = null;
		byte[] imageByte;
		
		String imageString = parts[0].split(",")[1];
		
		BASE64Decoder decoder = new BASE64Decoder();
		imageByte = decoder.decodeBuffer(imageString);
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();
		
		String filename = incrementCounter();
		String commonpath = "E:\\ImageData\\SignUpTemp\\";
		File outputfile = new File(commonpath + filename);
		ImageIO.write(image, "png", outputfile);
		
		try {
			Message obj = new Message(commonpath + filename);
			System.out.println(obj.getMessage());
			String[] userdata = obj.getMessage().split("@@");
			username = userdata[0];
			password = userdata[1];
			dob = userdata[2];
			name = userdata[3];
			ImageHandler imageHandler = new ImageHandlerImpl();
			int id = imageHandler.saveUser(username, name, dob);
			commonpath = "E:\\ImageData\\SignUpData\\";
			for(int i=1; i<=4; ++i) {
				String imgi = parts[i-1].split(",")[1];
				byte[] imageByte1 = decoder.decodeBuffer(imgi);
				ByteArrayInputStream bitArr = new ByteArrayInputStream(imageByte1);
				image = ImageIO.read(bitArr);
				bitArr.close();
				filename = id + "-" + username + i + ".png";
				outputfile = new File(commonpath + filename);
				ImageIO.write(image, "png", outputfile);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "done";
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
	@Path("checkUsername")
	public String checkUsername(String username) throws IOException{
		ImageHandler imageHandler = new ImageHandlerImpl();
		return imageHandler.checkUsername(username);
		
	}
	
	public static synchronized String incrementCounter() {
		counter++;
		String filename = "image" + counter +".png";
		return filename;
	}
}
