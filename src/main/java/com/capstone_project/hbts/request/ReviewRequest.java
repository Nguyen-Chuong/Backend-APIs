package com.capstone_project.hbts.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewRequest {

    private float cleanliness; // type

    private float facilities; // type

    private float location; // type

    private float service; // type

    private float valueForMoney; // type

    private String reviewTitle;

    private String reviewDetail;

    private int userBookingId;

}
