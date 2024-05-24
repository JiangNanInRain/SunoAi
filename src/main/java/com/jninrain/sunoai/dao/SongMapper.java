package com.jninrain.sunoai.dao;

import com.jninrain.sunoai.entity.Song;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/24/16:42
 * @Description:
 */
@Repository
public interface SongMapper extends Mapper<Song> {

}
