package com.jninrain.sunoai.controller;

import com.jninrain.sunoai.entity.PlayList;
import com.jninrain.sunoai.entity.Song;
import com.jninrain.sunoai.service.*;
import com.jninrain.sunoai.util.ParseObject.SongParseUtil;
import com.jninrain.sunoai.util.Result.Result;
import com.jninrain.sunoai.util.Result.ResultUtil;
import com.jninrain.sunoai.util.TokenParseUtil;
import com.jninrain.sunoai.vo.PlayListVO;
import com.jninrain.sunoai.vo.SongCardVO;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.HttpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private PlayList_SongService playList_songService;
    @Resource
    private SongService songService;

    @Resource
    private Song_User_LikeService song_user_likeService;

    @Resource
    private UserService userService;
    @ApiOperation("根据歌单Id查询歌单数据及歌曲")
    @GetMapping("{id}")
    public Result<PlayListVO> queryPlayListById(@PathVariable("id") Integer id,HttpServletRequest httpServletRequest ) throws ParseException {

        String user_id = TokenParseUtil.get(httpServletRequest.getHeader("token"),"uid");
        PlayListVO list = new PlayListVO();
        PlayList playList = playListService.getPlayListById(id);
        list.setImage(playList.getImage());
        list.setDescription(playList.getDescription());
        list.setId(playList.getId());
        list.setName(playList.getName());

        list.setUserName("王一哲");


        String[] songIds = playList_songService.getSongIdListById(id);

        List<SongCardVO> songList = new ArrayList<>();

        for(String songId:songIds){
            Boolean isLike = song_user_likeService.getLike(songId,user_id);
            SongCardVO songCardVO = toSongCardVO(songService.getSongById(songId));
            songCardVO.setIsLike(isLike);
            songList.add(songCardVO);
        }

        list.setSongs(songList);



        return ResultUtil.ok(list);
    }

    public SongCardVO toSongCardVO(Song song){

        String display_name = userService.getDisplayNameById(song.getUser_id());
        SongCardVO songCardVO = new SongCardVO();
        songCardVO.setTags(song.getTags());
        songCardVO.setDuration(song.getDuration());
        songCardVO.setId(song.getId());
        songCardVO.setTitle(song.getTitle());
        songCardVO.setImage_url(song.getImage_url());
        songCardVO.setDisplay_name(display_name);
        songCardVO.setPlay_count(song.getPlay_count());
        songCardVO.setUpvote_count(song.getUpvote_count());
        songCardVO.setImage_large_url(song.getImage_large_url());
        songCardVO.setAudio_url(song.getAudio_url());
        songCardVO.setLyrics(song.getLyrics());

        return songCardVO;
    }

}
