package com.example.ipanda1.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.example.ipanda1.model.Comment;

import java.util.Date;

/**
 * Created by Oubowu on 2017/12/14 10:41.
 */
@Entity(tableName = "comments",
        foreignKeys = {@ForeignKey(entity = ProductEntity.class,
                parentColumns = "id",
                childColumns = "productId",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "productId")})
public class CommentEntity implements Comment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int productId;

    private String text;

    private Date postedAt;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getProductId() {
        return productId;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Date getPostedAt() {
        return postedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public CommentEntity() {
    }

    @Ignore
    public CommentEntity(int id, int productId, String text, Date postedAt) {
        this.id = id;
        this.productId = productId;
        this.text = text;
        this.postedAt = postedAt;
    }
}
