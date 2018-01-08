package com.oubowu.ipanda.util;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Oubowu on 2017/12/20 16:34.
 * <p>五个分类的索引</p>
 * http://www.ipanda.com/kehuduan/xzbqy/index.json
 */
@Entity(tableName = "tab_index")
public class TabIndex {

    /**
     * title : 首页
     * noimage :
     * image : http://p1.img.cctvpic.com/photoAlbum/page/performance/img/2015/12/15/1450172469187_603.jpg
     * url : http://www.ipanda.com/kehuduan/shouye/index.json
     */

    @NonNull
    @PrimaryKey
    public String title = "";
    public String noimage;
    public String image;
    public String url;

    @Override
    public String toString() {
        return "TabIndex{" + "title='" + title + '\'' + ", noimage='" + noimage + '\'' + ", image='" + image + '\'' + ", url='" + url + '\'' + '}';
    }
}
