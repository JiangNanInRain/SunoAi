package com.jninrain.sunoai.controller;

import com.jninrain.sunoai.entity.PlayList;
import com.jninrain.sunoai.service.PlayListService;
import com.jninrain.sunoai.util.Result.Result;
import com.jninrain.sunoai.util.Result.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/06/12/16:57
 * @Description:
 */
@RestController
@RequestMapping("/playList")
public class PlayListController {
    @Resource
    private PlayListService playListService;

    @ApiOperation("根据Id查询歌单数据")
    @GetMapping("{id}")
    public Result<PlayList> queryPlayListById(@PathVariable("id") Integer id){
        PlayList list = playListService.getPlayListById(id);

        return ResultUtil.ok(list);
    }


}
