package com.jninrain.sunoai.service.impl;

import com.jninrain.sunoai.dao.PlayList_SongMapper;
import com.jninrain.sunoai.service.PlayList_SongService;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/13/11:40
 * @Description:
 */
@Service
public class PlayList_SongServiceImpl implements PlayList_SongService {
    @Resource
    private PlayList_SongMapper playList_songMapper;
    @Override
    public String[] getSongIdListById(Integer id) {
        return  playList_songMapper.selectSongIdListById(id);
    }
}
