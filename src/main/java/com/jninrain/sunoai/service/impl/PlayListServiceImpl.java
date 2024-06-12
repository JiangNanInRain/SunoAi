package com.jninrain.sunoai.service.impl;

import com.jninrain.sunoai.dao.PlayListMapper;
import com.jninrain.sunoai.entity.PlayList;
import com.jninrain.sunoai.service.PlayListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/12/17:17
 * @Description:
 */
@Service
public class PlayListServiceImpl implements PlayListService {
    @Resource
    private PlayListMapper playListMapper;
    @Override
    public PlayList getPlayListById(Integer id) {
        return playListMapper.selectPlayListById(id);
    }
}
