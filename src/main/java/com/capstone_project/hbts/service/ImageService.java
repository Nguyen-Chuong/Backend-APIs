package com.capstone_project.hbts.service;


import com.capstone_project.hbts.request.ImageRequest;

public interface ImageService {

    /**
     * Insert list image to a room type
     * @param imageRequest
     */
    void addListImageToRoomType(ImageRequest imageRequest);

    /**
     * get total number image of a room type
     * @param roomTypeId
     */
    Integer getTotalNumberRoomTypeImage(int roomTypeId);

}
