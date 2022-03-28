package com.capstone_project.hbts.service.impl;

import com.capstone_project.hbts.entity.Image;
import com.capstone_project.hbts.entity.RoomType;
import com.capstone_project.hbts.repository.ImageRepository;
import com.capstone_project.hbts.repository.RoomTypeRepository;
import com.capstone_project.hbts.request.ImageRequest;
import com.capstone_project.hbts.service.ImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Log4j2
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final RoomTypeRepository roomTypeRepository;

    public ImageServiceImpl(ImageRepository imageRepository, RoomTypeRepository roomTypeRepository) {
        this.imageRepository = imageRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public void addListImageToRoomType(ImageRequest imageRequest) {
        log.info("Request to add list image to room type");
        // get list source url
        List<String> list = imageRequest.getSourceUrl();
        // create a list image
        List<Image> listImage = new ArrayList<>();
        for (String src : list) {
            Image image = new Image();
            image.setSrc(src);
            // create new room type
            RoomType roomType = new RoomType();
            roomType.setId(imageRequest.getRoomTypeId());
            // set room type
            image.setRoomType(roomType);
            // add all of them to list
            listImage.add(image);
        }
        // batch processing
        imageRepository.saveAll(listImage);
    }

    @Override
    public Integer getTotalNumberRoomTypeImage(int roomTypeId) {
        log.info("Request to get total number image of a room type");
        return imageRepository.getTotalNumberOfRoomImage(roomTypeId);
    }

    @Override
    public void deleteListImage(int roomTypeId) {
        log.info("Request to delete a list image of a room type");
        RoomType roomType = roomTypeRepository.getRoomTypeById(roomTypeId);
        // get list image in this room
        Set<Image> imageSet = roomType.getListImage();
        // delete all
        imageRepository.deleteAll(imageSet);
    }

}
