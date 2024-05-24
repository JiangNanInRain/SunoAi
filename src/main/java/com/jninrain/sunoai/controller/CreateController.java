package com.jninrain.sunoai.controller;

import com.alibaba.fastjson.JSONObject;
import com.jninrain.sunoai.entity.Song;
import com.jninrain.sunoai.service.SongService;
import com.jninrain.sunoai.vo.LyricsVO;
import com.jninrain.sunoai.vo.SongVO;
import com.jninrain.sunoai.request.GenerateMusicByPromptRequest;
import com.jninrain.sunoai.request.GenerateMusicCustomMode;
import com.jninrain.sunoai.util.ParseObject.SongParseUtil;
import com.jninrain.sunoai.util.Result.Result;
import com.jninrain.sunoai.util.Result.ResultUtil;
import com.jninrain.sunoai.util.SunoApiUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

/**
 * 音乐创作接口
 *
 * @Auther: fei
 * @Date: 2024/05/23/09:20
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/create")
public class CreateController {

    @Resource
    private SongService songService;
    /**
     * 仅根据prompt生成音乐
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @ApiOperation("仅根据prompt生成音乐")
    @PostMapping("/generateMusicOnlyByPrompt")
    public Result<List<SongVO>> generateMusicOnlyByPrompt(@RequestBody GenerateMusicByPromptRequest request) throws InterruptedException, ParseException {
//        log.info(request.getPrompt());
//        log.info(request.getIsAbsoluteMusic().toString());
//        log.info(request.getModel_name());
//        JSONObject respond = SunoApiUtil.generateMusicOnlyByPrompt(request.getPrompt(),request.getIsAbsoluteMusic(),request.getModel_name());
//        String song_id1 =    respond.getJSONArray("data")
//                            .getJSONObject(0)
//                            .getString("song_id");
//        String song_id2 =    respond.getJSONArray("data")
//                .getJSONObject(1)
//                .getString("song_id");
//        log.info("返回song_id1:"+song_id1);
//        log.info("返回song_id2:"+song_id2);
        List<SongVO> songVOList = new ArrayList<>(2);
//        String status1 = getStatus(song_id1);
//        String status2 = getStatus(song_id2);
//        if("error".equals(status1)||"error".equals(status2)){
//            return ResultUtil.fail("生成失败");
//        }
        SongVO songVO1 =  SongParseUtil.queryOneSong("48e8330a-35f0-4431-8120-92e7318bb83b");
        SongVO songVO2 = SongParseUtil.queryOneSong("d172ac7b-8bbe-4e2a-a96e-09bb15124da1");
        songVOList.add(songVO1);
        songVOList.add(songVO2);

        songService.addOneSong(toSong(songVO1));
        songService.addOneSong(toSong(songVO2));

        return ResultUtil.ok(songVOList);
    }

    /**
     * 自定义生成音乐模式
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @ApiOperation("自定义生成音乐模式")
    @PostMapping("/generateMusicCustomMode")
    public Result<List<SongVO>> generateMusicCustomMode(@RequestBody GenerateMusicCustomMode request) throws InterruptedException, ParseException {
//        log.info("标题:"+request.getTitle());
//        log.info("风格:"+request.getTags());
//        log.info("歌词或提示词:"+request.getPrompt());
//        log.info("模型名称:"+request.getModel_name());
//
//        JSONObject req = new JSONObject();
//        req.put("title",request.getTitle());
//        req.put("tags",request.getTags());
//        req.put("prompt",request.getPrompt());
//        req.put("model_name",request.getModel_name());
//
//        JSONObject respond = SunoApiUtil.generateMusic(req);
//
//        String song_id1 =    respond.getJSONArray("data")
//                .getJSONObject(0)
//                .getString("song_id");
//        String song_id2 =    respond.getJSONArray("data")
//                .getJSONObject(1)
//                .getString("song_id");
//        log.info("返回song_id1:"+song_id1);
//        log.info("返回song_id2:"+song_id2);
        List<SongVO> songVOList = new ArrayList<>(2);
//       String status1 =  getStatus(song_id1);
//        String status2 =  getStatus(song_id2);
//        if("error".equals(status1)||"error".equals(status2)){
//            return ResultUtil.fail("生成失败");
//        }
        SongVO songVO1 = SongParseUtil.queryOneSong("17d22b1d-67ad-4971-a4c1-ad802b6eb7fc");
        SongVO songVO2 = SongParseUtil.queryOneSong( "faa76e7e-191b-46a4-9983-31760e4def5c");
        songVOList.add(songVO1);
        songVOList.add(songVO2);

        songService.addOneSong(toSong(songVO1));
        songService.addOneSong(toSong(songVO2));

        return ResultUtil.ok(songVOList);
    }

    /**
     * 生成歌词
     *
     * @param prompt       歌词提示（可为空）
     * @return
     * @throws InterruptedException
     */
    @ApiOperation("生成歌词")
    @PostMapping("/generateLyrics")
    public Result<LyricsVO>  generateLyricsByPrompt(@RequestBody String prompt) throws InterruptedException {
        log.info("歌词提示词:"+prompt);

        JSONObject respond = SunoApiUtil.generateLyricsByPrompt(prompt);
        log.info("歌词生成返回结果:"+respond.toString());
        String lyrics_id = respond.getJSONObject("data")
                                    .getString("id");
        log.info("歌词Id:"+lyrics_id);

        String status = getLyricsStatus(lyrics_id);
        if("error".equals(status)){
            return ResultUtil.fail("生成失败");
        }
        JSONObject lyricsRespond = SunoApiUtil.queryLyrics(lyrics_id);
        log.info("歌词结果: "+lyricsRespond.toString());
        LyricsVO lyricsVO = new LyricsVO();
        JSONObject data = lyricsRespond.getJSONObject("data");
        lyricsVO.setTitle(data.getString("title"));
        lyricsVO.setLyrics(data.getString("text"));
        System.out.println(lyricsVO);

        return ResultUtil.ok(lyricsVO);
    }

    /**
     * 随机生成风格标签
     *
     * @return
     */
    @ApiOperation("随机生成风格标签")
    @GetMapping("/generateRandomStyle")
    public Result<String> generateRandomStyle(){
        String style ;
        Random ran = new Random();
        int num = ran.nextInt(10)+1;//[1,10]

        switch (num){
            case 1:
                style = "smooth dance";
                break;
            case 2:
                style = "mellow uk garage";
                break;
            case 3:
                style = "melodic cumbia";
                break;
            case 4:
                style = "mellow house";
                break;
            case 5:
                style = "bouncy anime";
                break;
            case 6:
                style = "chill bedroom pop";
                break;
            case 7:
                style = "powerful uk garage";
                break;
            case 8:
                style = "melodic delta blues";
                break;
            case 9:
                style = "chill reggae";
                break;
            case 10:
                style = "dreamy swing";
                break;
            default:
                style = "melodic edm";
        }


        return  ResultUtil.ok(style);
    }
    public static String getStatus(String id) throws InterruptedException {
        net.sf.json.JSONArray json   = null;

        net.sf.json.JSONObject song = null;
         int i = 0;
        String status = "";
        Thread thread = Thread.currentThread();
        do{
            json =  SunoApiUtil.queryGenerateResult(id);
            if (json != null) {
                song = (net.sf.json.JSONObject) json.get(0);
            }
            status = song.getString("status");
            thread.sleep(6000);
            System.out.println("-----"+i+++"----");
        }while (!status.equals("complete")&&!status.equals("error"));

        return status;

    }

    public static String getLyricsStatus(String lid) throws InterruptedException {



         JSONObject lyrics = null;
        int i = 0;
        String status = "";
        Thread thread = Thread.currentThread();
        do{
            lyrics = SunoApiUtil.queryLyrics(lid);
            if(null!=lyrics){
                status = lyrics.getJSONObject("data").getString("status");
            }
            thread.sleep(2000);
        }while (!status.equals("complete")&&!status.equals("error"));
        return status;
    }

    public static Song toSong(SongVO songVO){


        Song song = new Song();
        song.setSong_id(songVO.getSong_id());
        song.setTitle(songVO.getTitle());
        song.setLyrics(songVO.getLyrics());
        song.setTags(songVO.getTags());
        song.setUser_id(songVO.getUser_id());
        song.setCreated_time(songVO.getCreated_time());
        song.setVideo_url(songVO.getVideo_url());
        song.setAudio_url(songVO.getAudio_url());
        song.setImage_url(songVO.getImage_url());
        song.setImage_large_url(songVO.getImage_large_url());
        song.setDuration(songVO.getDuration());

        return song;

    }
}
