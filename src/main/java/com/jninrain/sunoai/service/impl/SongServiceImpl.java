package com.jninrain.sunoai.service.impl;

import com.jninrain.sunoai.dao.SongMapper;
import com.jninrain.sunoai.entity.Song;
import com.jninrain.sunoai.service.SongService;
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
}
