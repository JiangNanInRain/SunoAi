package com.jninrain.sunoai.service.impl;

import com.jninrain.sunoai.dao.TagMapper;
import com.jninrain.sunoai.entity.Tag;
import com.jninrain.sunoai.service.TagService;
import org.springframework.stereotype.Service;
import springfox.documentation.service.Tags;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/11/16:26
 * @Description:
 */
@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;

    @Override
    public int insertOneTag(String tag) {
        Tag t = new Tag();
        t.setContent(tag);

        return  tagMapper.insert(t);
    }

    @Override
    public int insertTagList(String[] tags) {
        Tag tag = new Tag();
        for(String t:tags){
            if(isExist(t))continue;
            tag.setContent(t);
            tagMapper.insert(tag);
        }
        return  1;
    }

    @Override
    public boolean isExist(String tag) {
        if(null == tagMapper.selectByContent(tag)){
            return false;
        }
        return true;
    }

    @Override
    public Tag[] selectByRandom(int limit) {
        return tagMapper.selectByRandom(limit);
    }
}
