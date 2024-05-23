package com.jninrain.sunoai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jninrain.sunoai.entity.Song;
import com.jninrain.sunoai.request.GenerateMusicByPromptRequest;
import com.jninrain.sunoai.util.ParseObject.SongParseUtil;
import com.jninrain.sunoai.util.Result.Result;
import com.jninrain.sunoai.util.Result.ResultUtil;
import com.jninrain.sunoai.util.SunoApiUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 音乐创作
 *
 * @Auther: fei
 * @Date: 2024/05/23/09:20
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/create")
public class CreateController {

    @ApiModelProperty("仅根据prompt生成音乐")
    @PostMapping("/generateMusicOnlyByPrompt")
    public Result<List<Song>> generateMusicOnlyByPrompt(@RequestBody GenerateMusicByPromptRequest request) throws InterruptedException {
        log.info(request.getPrompt());
        log.info(request.getIsAbsoluteMusic().toString());
        log.info(request.getModel_name());
        JSONObject respond = SunoApiUtil.generateMusicOnlyByPrompt(request.getPrompt(),request.getIsAbsoluteMusic(),request.getModel_name());
        String song_id1 =    respond.getJSONArray("data")
                            .getJSONObject(0)
                            .getString("song_id");
        String song_id2 =    respond.getJSONArray("data")
                .getJSONObject(1)
                .getString("song_id");
        log.info("返回song_id1:"+song_id1);
        log.info("返回song_id2:"+song_id2);
        List<Song> songList  = new ArrayList<>(2);
        String status1 = getStatus(song_id1);
        songList.add(SongParseUtil.queryOneSong(song_id1));
        String status2 = getStatus(song_id2);
        songList.add(SongParseUtil.queryOneSong(song_id2));

        return ResultUtil.ok(songList);
    }

    @ApiModelProperty("自定义生成音乐模式")
    @PostMapping("/generateMusicCustomMode")
    public Result<List<Song>> generateMusicCustomMode(){


        return ResultUtil.ok();
    }


    public static String getStatus(String id) throws InterruptedException {
        net.sf.json.JSONArray json   = null;

        net.sf.json.JSONObject song = null;

        String status = "";
        Thread thread = Thread.currentThread();
        do{
            json =  SunoApiUtil.queryGenerateResult(id);
            if (json != null) {
                song = (net.sf.json.JSONObject) json.get(0);
            }
            status = song.getString("status");
            thread.sleep(9000);
        }while (!status.equals("complete")&&!status.equals("error"));

        return status;

    }
}
