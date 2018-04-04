package com.facesteg.services;

import com.facesteg.dao.ImageHandler;
import com.facesteg.dao.ImageHandlerImpl;

public class ImageHandleServiceImpl implements ImageHandleService {

	@Override
	public void setImage(int number, String name) {
		ImageHandler imgDAO= new ImageHandlerImpl();
		imgDAO.saveName(number, name);
	}

	@Override
	public String getImage(int number) {
		ImageHandler imgDAO= new ImageHandlerImpl();
		return imgDAO.getImage(number);
	}
	
	
}
