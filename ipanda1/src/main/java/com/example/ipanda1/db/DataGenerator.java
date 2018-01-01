package com.example.ipanda1.db;

import com.example.ipanda1.db.entity.CommentEntity;
import com.example.ipanda1.db.entity.ProductEntity;
import com.example.ipanda1.model.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Oubowu on 2017/12/14 11:27.<P></P>
 * 伪造数据
 */
public class DataGenerator {

    private static final String[] FIRST = new String[]{"特卖", "新品", "特价", "优质", "二手"};

    private static final String[] SECOND = new String[]{"三头猴", "橡皮鸡", "品脱烈酒", "Monocle杂志"};

    private static final String[] DESCRIPTION = new String[]{"终于来了", "是Stan S. Stanman推荐的", "在Mêlée Island是最畅销的产品", "是 \uD83D\uDCAF", "是 ❤️", "是 上等的"};

    private static final String[] COMMENTS = new String[]{"评论 1", "评论 2", "评论 3", "评论 4", "评论 5", "评论 6"};

    public static List<ProductEntity> generateProducts() {
        List<ProductEntity> products = new ArrayList<>(FIRST.length * SECOND.length);
        Random rnd = new Random();
        for (int i = 0; i < FIRST.length; i++) {
            for (int j = 0; j < SECOND.length; j++) {
                ProductEntity product = new ProductEntity();
                product.setName(FIRST[i] + " " + SECOND[j]);
                product.setDescription(product.getName() + " " + DESCRIPTION[j]);
                product.setPrice(rnd.nextInt(240));
                product.setId(FIRST.length * i + j + 1);
                products.add(product);
            }
        }
        return products;
    }

    public static List<CommentEntity> generateCommentsForProducts(final List<ProductEntity> products) {
        List<CommentEntity> comments = new ArrayList<>();
        Random rnd = new Random();

        for (Product product : products) {
            int commentsNumber = rnd.nextInt(5) + 1;
            for (int i = 0; i < commentsNumber; i++) {
                CommentEntity comment = new CommentEntity();
                comment.setProductId(product.getId());
                comment.setText(COMMENTS[i] + " for " + product.getName());
                comment.setPostedAt(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(commentsNumber - i) + TimeUnit.HOURS.toMillis(i)));
                comments.add(comment);
            }
        }

        return comments;

    }

}
