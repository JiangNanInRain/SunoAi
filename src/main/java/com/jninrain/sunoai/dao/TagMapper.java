package com.jninrain.sunoai.dao;

import com.jninrain.sunoai.entity.Tag;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/11/16:37
 * @Description:
 */
@Repository
public interface TagMapper extends Mapper<Tag> {
    @Select("select * from Tags where content = #{content}")
    Tag selectByContent(String content);

    @Select("select  * from Tags order by rand() limit 0,#{limit} ")
    Tag[] selectByRandom(int limit);
}
