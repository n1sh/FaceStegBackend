package com.facesteg.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.facesteg.services.ImageHandleService;
import com.facesteg.services.ImageHandleServiceImpl;

@Path("uploadImage")
public class ImageController {
	public static void main(String[] args) {
//		ImageHandleService imgService = new ImageHandleServiceImpl();
//		imgService.setImage(2, "car.png");
//		
//		String name = imgService.getImage(2);
//		System.out.println(name);
		
		File x = new File("/wxyzuv");
		System.out.println(x.mkdir());
	}
	
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String uploadDirectoryDocument(HttpServletRequest request,HttpServletResponse response,
		@QueryParam("user") String username,
		@QueryParam("img") String imgData) throws IOException{
		System.out.println(imgData);
		
		return "done";
	}

}
