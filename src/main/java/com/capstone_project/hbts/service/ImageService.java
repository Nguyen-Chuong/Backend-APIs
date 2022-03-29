package com.capstone_project.hbts.service;

import com.capstone_project.hbts.request.ImageRequest;

public interface ImageService {

    /**
     * Insert list image to a room type
     */
    void addListImageToRoomType(ImageRequest imageRequest);

    /**
     * get total number image of a room type
     */
    Integer getTotalNumberRoomTypeImage(int roomTypeId);

    /**
     * delete a list image in provider's room type
     */
    void deleteListImage(int roomTypeId);

}
