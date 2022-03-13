package com.capstone_project.hbts.response;

import org.springframework.data.domain.PageImpl;

import java.util.List;

public class CustomPageImpl<T> extends PageImpl<T> {

    public CustomPageImpl(List<T> content) {
        super(content);
    }

}
