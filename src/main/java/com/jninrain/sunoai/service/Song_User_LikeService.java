package com.jninrain.sunoai.service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/12/15:07
 * @Description:
 */
public interface Song_User_LikeService {

    //点赞
    void insertLike(String song_id,String user_id);
    //取消点赞
    void deleteLike(String song_id,String user_id);
    //查询
    boolean getLike(String song_id,String user_id);
    String[] getSongIdListByUserId(Long id);
}
