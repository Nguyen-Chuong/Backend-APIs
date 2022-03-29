package com.capstone_project.hbts.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
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
