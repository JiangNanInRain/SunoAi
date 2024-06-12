package com.jninrain.sunoai.service.impl;

import com.jninrain.sunoai.dao.Song_User_LikeMapper;
import com.jninrain.sunoai.entity.Song_User_Like;
import com.jninrain.sunoai.service.Song_User_LikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/12/15:14
 * @Description:
 */
@Service
public class Song_User_LikeServiceImpl implements Song_User_LikeService {
    @Resource
    private Song_User_LikeMapper song_user_likeMapper;

    @Override
    public void insertLike(String song_id, String user_id) {
        Song_User_Like like = new Song_User_Like();
        like.setUser_id(user_id);
        like.setSong_id(song_id);
        song_user_likeMapper.insert(like);
    }

    @Override
    public void deleteLike(String song_id, String user_id) {
        Song_User_Like like = new Song_User_Like();
        like.setUser_id(user_id);
        like.setSong_id(song_id);
        song_user_likeMapper.delete(like);
    }

    @Override
    public boolean getLike(String song_id, String user_id) {
        System.out.println(song_user_likeMapper.getLike(user_id,song_id));
        if(  song_user_likeMapper.getLike(user_id,song_id) == null){
            return false;
        }
        return true;
    }
}
