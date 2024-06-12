package com.jninrain.sunoai.dao;

import com.jninrain.sunoai.entity.PlayList;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/12/17:13
 * @Description:
 */
@Repository
public interface PlayListMapper extends Mapper<PlayList> {

    @Select("Select * from PlayList where id = #{id}")
    PlayList selectPlayListById(Integer id);
}
