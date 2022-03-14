package com.capstone_project.hbts.service;


import com.capstone_project.hbts.dto.ImageDTO;
import com.capstone_project.hbts.request.ImageRequest;

import java.util.List;

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

    /**
     * Update an image in list room type image
     * @param imageDTO
     */
    void updateImage(ImageDTO imageDTO);

    /**
     * delete a list image in provider's room type
     * @param imageIds
     */
    void deleteListImage(List<Integer> imageIds);

}
