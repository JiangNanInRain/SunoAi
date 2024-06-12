package com.jninrain.sunoai.service.impl;

import com.jninrain.sunoai.dao.SongMapper;
import com.jninrain.sunoai.dao.Song_TagsMapper;
import com.jninrain.sunoai.dao.TagMapper;
import com.jninrain.sunoai.entity.Song_Tags;
import com.jninrain.sunoai.entity.Tag;
import com.jninrain.sunoai.service.Song_TagService;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/11/17:21
 * @Description:
 */
@Service
public class Song_TagServiceImpl implements Song_TagService {
    @Resource
    private Song_TagsMapper song_tagsMapper;
    @Resource
    private TagMapper tagMapper;
    @Override
    public void insertSongTagsByTagName(String song_id, String tag) {


        int tag_id = tagMapper.selectByContent(tag).getId();;
        Song_Tags song_tags = new Song_Tags();
        song_tags.setSong_id(song_id);
        song_tags.setTag_id(tag_id);
        song_tagsMapper.insert(song_tags);
    }
}
