package com.jninrain.sunoai.service;

import com.jninrain.sunoai.entity.Tag;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/11/16:18
 * @Description:
 */
public interface TagService {
    //单条插入
    int insertOneTag(String tag);

    //批量插入
     int insertTagList(String[] tags);

     boolean isExist(String tag);

     Tag[] selectByRandom(int limit);

}
