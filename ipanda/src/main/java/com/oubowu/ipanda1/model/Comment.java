package com.oubowu.ipanda1.model;

import java.util.Date;

/**
 * Created by Oubowu on 2017/12/14 10:39.
 */
public interface Comment {

    int getId();

    int getProductId();

    String getText();

    Date getPostedAt();

}
