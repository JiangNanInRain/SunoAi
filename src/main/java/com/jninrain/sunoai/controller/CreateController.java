package com.jninrain.sunoai.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jninrain.sunoai.entity.Song;
import com.jninrain.sunoai.entity.Tag;
import com.jninrain.sunoai.service.SongService;
import com.jninrain.sunoai.service.Song_TagService;
import com.jninrain.sunoai.service.TagService;
import com.jninrain.sunoai.util.Http.HttpCommon;
import com.jninrain.sunoai.util.TokenParseUtil;
import com.jninrain.sunoai.vo.LyricsVO;
import com.jninrain.sunoai.vo.SongVO;
import com.jninrain.sunoai.request.GenerateMusicByPromptRequest;
import com.jninrain.sunoai.request.GenerateMusicCustomMode;
import com.jninrain.sunoai.util.ParseObject.SongParseUtil;
import com.jninrain.sunoai.util.Result.Result;
import com.jninrain.sunoai.util.Result.ResultUtil;
import com.jninrain.sunoai.util.SunoApiUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
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
    @Resource
    private TagService tagService;
    @Resource
    private Song_TagService song_tagService;
    /**
     * 仅根据prompt生成音乐
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @ApiOperation("仅根据prompt生成音乐")
    @PostMapping("/generateMusicOnlyByPrompt")
    public Result<List<SongVO>> generateMusicOnlyByPrompt(HttpServletRequest httpServletRequest, @RequestBody GenerateMusicByPromptRequest request) throws InterruptedException, ParseException {
        log.info("["+httpServletRequest.getRemoteHost()+" ]访问接口：/create/generateMusicOnlyByPrompt");
        //JSONObject respond = SunoApiUtil.generateMusicOnlyByPrompt(request.getPrompt(),request.getIsAbsoluteMusic(),request.getModel_name());
//        String song_id1 =    respond.getJSONArray("data")
//                            .getJSONObject(0)
//                            .getString("song_id");
//        String song_id2 =    respond.getJSONArray("data")
//                .getJSONObject(1)
//                .getString("song_id");
//       log.info("返回song_id1:"+song_id1);
//        log.info("返回song_id2:"+song_id2);
        List<SongVO> songVOList = new ArrayList<>(2);
//        String status1 = getStatus(song_id1);
//        String status2 = getStatus(song_id2);
//        if("error".equals(status1)||"error".equals(status2)){
//            return ResultUtil.fail("生成失败");
//       }
 //    String user_id = TokenParseUtil.get(httpServletRequest.getHeader("token"),"uid");
        Song song1 =  SongParseUtil.queryOneSong("468d9eb4-a4f5-4777-950a-7f30e55b9154");
        Song song2 = SongParseUtil.queryOneSong("d172ac7b-8bbe-4e2a-a96e-09bb15124da1");
 //       song1.setUser_id(user_id);
 //       song2.setUser_id(user_id);
        songVOList.add(toSongVO(song1));
        songVOList.add(toSongVO(song2));

        //save(song1);
        //save(song2);

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
    public Result<List<SongVO>> generateMusicCustomMode(HttpServletRequest httpServletRequest,@RequestBody GenerateMusicCustomMode request) throws InterruptedException, ParseException {
          log.info("["+httpServletRequest.getRemoteHost()+" ]访问接口：/create/generateMusicCustomMode");
        JSONObject req = new JSONObject();
        req.put("title",request.getTitle());
        req.put("tags",request.getTags());
        req.put("prompt",request.getPrompt());
        req.put("model_name",request.getModel_name());

       // JSONObject respond = SunoApiUtil.generateMusic(req);

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
//           return ResultUtil.fail("生成失败");
//       }
 //       String user_id = TokenParseUtil.get(httpServletRequest.getHeader("token"),"uid");
        Song song1 =  SongParseUtil.queryOneSong("468d9eb4-a4f5-4777-950a-7f30e55b9154");
        Song song2 = SongParseUtil.queryOneSong("d172ac7b-8bbe-4e2a-a96e-09bb15124da1");
   //     song1.setUser_id(user_id);
     //   song2.setUser_id(user_id);
        songVOList.add(toSongVO(song1));
        songVOList.add(toSongVO(song2));

//        save(song1);
//        save(song2);

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
    public Result<LyricsVO>  generateLyricsByPrompt(HttpServletRequest httpServletRequest,@RequestBody String prompt) throws InterruptedException {
        log.info("["+httpServletRequest.getRemoteHost()+" ]访问接口：/create/generateLyrics");

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
    public Result<List<String>> generateRandomStyle(HttpServletRequest httpServletRequest){
        log.info("["+httpServletRequest.getRemoteHost()+" ]访问接口：/create/generateRandomStyle");
        Tag[] tags = tagService.selectByRandom(5);
        List<String> res = new ArrayList<>();
        int i=0;
        for (Tag tag:tags){
            res.add(tag.getContent());
        }

        return  ResultUtil.ok(res);
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
            thread.sleep(1000);
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
            thread.sleep(1000);
        }while (!status.equals("complete")&&!status.equals("error"));
        return status;
    }

    public static SongVO toSongVO(Song song){


        SongVO songVO = new SongVO();
        songVO.setSong_id(song.getId());
        songVO.setUser_id(song.getUser_id());
        songVO.setTitle(song.getTitle());
        songVO.setModel_version(song.getMajor_model_version());
        songVO.setCreated_time(songVO.getCreated_time());
        songVO.setTags(song.getTags());
        songVO.setVideo_url(song.getVideo_url());
        songVO.setAudio_url(song.getAudio_url());
        songVO.setImage_url(song.getImage_url());
        songVO.setImage_large_url(song.getImage_large_url());
        songVO.setDuration(song.getDuration());
        songVO.setLyrics(song.getLyrics());


        return songVO;

    }

    @GetMapping("/save")
    public Result<JSONObject> save(HttpServletRequest httpServletRequest, String playList_Id,int page) throws InterruptedException, ParseException, IOException, URISyntaxException {

//        int page=0;
//        while (page>=0) {
            JSONObject json = HttpCommon.getHttpRequestFastJson("https://studio-api.suno.ai/api/playlist/" + playList_Id + "/?page="+page, "", null, null);
            JSONArray jsonArray = json.getJSONArray("playlist_clips");
//            if(jsonArray.size()==0){
//                break;
//            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject songJSON = jsonArray.getJSONObject(i);
                JSONObject clip = songJSON.getJSONObject("clip");
                System.out.println(clip.getString("id"));
                Song song = SongParseUtil.queryOneSong(clip.getString("id"));
                songService.addOneSong(song);
            }
//            page++;
//        }



        return ResultUtil.ok(json);
    }


    public  void save(Song song){
        songService.addOneSong(song);
        tagService.insertTagList(song.getTags());
        for(String tag:song.getTags()){
            song_tagService.insertSongTagsByTagName(song.getId(),tag);
        }
    }
}
