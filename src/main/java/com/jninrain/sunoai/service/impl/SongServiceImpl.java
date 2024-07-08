package com.jninrain.sunoai.service.impl;

import com.jninrain.sunoai.dao.SongMapper;
import com.jninrain.sunoai.entity.Song;
import com.jninrain.sunoai.service.SongService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/24/16:44
 * @Description:
 */

@Service
public class SongServiceImpl implements SongService {

    @Resource
    private SongMapper songMapper;

    @Override
    public void addOneSong(Song song) {
        songMapper.insert(song);
    }

    @Override
    public List<Song> getList() {
        return songMapper.selectAll();
    }


    @Override
    public void deleteOneSong(String song_id) {
         songMapper.deleteByPrimaryKey(song_id);
    }

    @Override
    public Song getSongById(String id) {
        return songMapper.getSongById(id);
    }

    @Override
    public String getPlayAudioUrl(String id) {
        return songMapper.selectAudioUrlById(id);
    }

    @Override
    public void updatePlayCountPlus(String id) {
        songMapper.updatePlayCountPlus(id);
    }

//    @Override
//    public void updatePlayCountSub(String id) {
//        songMapper.updatePlayCountSub(id);
//    }


    @Override
    public void upvote(String id) {
        songMapper.upvote(id);
    }

    @Override
    public void cancelVote(String id) {
        songMapper.cancelVote(id);
    }

    @Override
    public String[] getSongIdListByUserId(Long id) {
        return songMapper.getSongIdListByUserId(id);
    }
}
