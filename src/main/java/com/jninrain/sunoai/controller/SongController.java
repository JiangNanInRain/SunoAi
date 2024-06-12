package com.jninrain.sunoai.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jninrain.sunoai.entity.Song;
import com.jninrain.sunoai.entity.Song_User_Like;
import com.jninrain.sunoai.service.SongService;
import com.jninrain.sunoai.service.Song_User_LikeService;
import com.jninrain.sunoai.service.TagService;
import com.jninrain.sunoai.service.UserService;
import com.jninrain.sunoai.util.Excel.ExportUtil;
import com.jninrain.sunoai.util.Result.Result;
import com.jninrain.sunoai.util.Result.ResultUtil;
import com.jninrain.sunoai.util.TokenParseUtil;
import com.jninrain.sunoai.vo.SongCardVO;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.Default;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/30/10:28
 * @Description:
 */
@RestController
@RequestMapping("/song")
public class SongController {

    @Resource
    private SongService songService;
    @Resource
    private TagService tagService;
    @Resource
    private UserService userService;
    @Resource
    private Song_User_LikeService song_user_likeService;


    @ApiOperation("播放歌曲ById(播放数增加，返回音频链接)")
    @GetMapping("/play")
    public String playAudio(String song_id){
        songService.updatePlayCountPlus(song_id);
        return  songService.getPlayAudioUrl(song_id) ;
    }

    @ApiOperation("点赞")
    @GetMapping("/upvote")
    public boolean upvote(HttpServletRequest httpServletRequest, String song_id){
        String user_id = TokenParseUtil.get(httpServletRequest.getHeader("token"),"uid");
        if(song_user_likeService.getLike(song_id,user_id)){

            return false;
        }
        song_user_likeService.insertLike(song_id,user_id);
        songService.upvote(song_id);
        return true;
    }

    @ApiOperation("取消点赞")
    @GetMapping("/cancelVote")
    public boolean cancelVote(HttpServletRequest httpServletRequest,String song_id){
        String user_id = TokenParseUtil.get(httpServletRequest.getHeader("token"),"uid");
        if(song_user_likeService.getLike(song_id,user_id)){
            song_user_likeService.deleteLike(song_id,user_id);
            songService.cancelVote(song_id);
            return true;
        }
        return false;
    }

    @ApiOperation("根据歌曲Id得到SongCard")
    @GetMapping("/querySongCardById")
    public Result<SongCardVO> querySongCardById(HttpServletRequest httpServletRequest,String id){
        String user_id = TokenParseUtil.get(httpServletRequest.getHeader("token"),"uid");
        Boolean isLike = song_user_likeService.getLike(id,user_id);
        Song song = songService.getSongById(id);
        SongCardVO songCardVO = toSongCardVO(song);
        songCardVO.setIsLike(isLike);
        return ResultUtil.ok(songCardVO);
    }

    @ApiOperation("分页请求SongCard(可根据热度榜种类筛选)")
    @GetMapping("/querySongCardListByPage")
    public PageInfo<SongCardVO> querySongCardListByPage(Integer pageNum, Integer pageSize, @Param("NOW,WEEKLY,MONTHLY,ALLTIME字符串选择热度榜")  String hotDateType){
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);

        //需要分页的内容
        List<SongCardVO> list = null;

        PageInfo<SongCardVO> pageInfo = new PageInfo<SongCardVO>(list);
        return pageInfo;
    }

    @GetMapping("/exportSongExcel")
    public Map<String, Object> exportSongExcel(HttpServletResponse response){

        //设置返回结果格式
        response.setContentType("application/binary;charset=ISO8859_1");

        try{
            ServletOutputStream outputStream = response.getOutputStream();

            String excelName = "歌曲导出表";
            String[] titles = { "歌曲Id", "标题", "歌词", "风格", "用户Id", "视频链接", "音频链接","图片链接","大图片链接","时长","创建时间" };

            String fileName = new String(excelName.getBytes(), "ISO8859_1");
            // 组装附件名称和格式
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");



            List<Song> songList =songService.getList();

            //创建Excel
            //创建一个WorkBook对应一个文件
            XSSFWorkbook workBook = new XSSFWorkbook();

            //添加sheet
            XSSFSheet sheet = workBook.createSheet(excelName);
            //设置表的默认宽度
            sheet.setDefaultColumnWidth(20);
            //设置表的默认高度
            sheet.setDefaultRowHeightInPoints(35);

            ExportUtil exportUtil =new ExportUtil(workBook,sheet);
            XSSFCellStyle headStyle = exportUtil.getAttendanceHeadStyle();
            XSSFCellStyle titleStyle = exportUtil.getTittleStyle();
            XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();

            //构建表头
            XSSFRow headRow = sheet.createRow(0);
            XSSFCell cell =  headRow.createCell(0);
            cell.setCellStyle(headStyle);
            cell.setCellValue("歌曲记录");

            //合并单元格
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,titles.length-1));

            //构建表头
            XSSFRow titleRow = sheet.createRow(1);
            for(int i = 0;i<titles.length;i++){
                cell =titleRow.createCell(i);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(titles[i]);
            }

            //对表赋值
            if(null!=songList&&songList.size()>0){
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                //循环赋值
                for(int i = 0;i <songList.size();i++){
                    XSSFRow bodyRow = sheet.createRow(i+2);

                    Song song = songList.get(i);

                    //歌曲Id
                    int j = 0;
                    cell = bodyRow.createCell(j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(song.getId());

                    //标题
                    cell = bodyRow.createCell(++j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(song.getTitle());

                    //歌词
                    cell = bodyRow.createCell(++j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(song.getLyrics());



                    //用户Id
                    cell = bodyRow.createCell(++j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(song.getUser_id());

                    //视频链接
                    cell = bodyRow.createCell(++j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(song.getVideo_url());

                    //音频链接
                    cell = bodyRow.createCell(++j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(song.getAudio_url());

                    //图片链接
                    cell = bodyRow.createCell(++j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(song.getImage_url());

                    //大图片链接
                    cell = bodyRow.createCell(++j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(song.getImage_large_url());

                    //时长
                    cell = bodyRow.createCell(++j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(song.getDuration());

                    //创建时间
                    cell = bodyRow.createCell(++j);
                    cell.setCellStyle(bodyStyle);
                    cell.setCellValue(formatter.format(song.getCreated_at()) );
                }

            }

            try{
                workBook.write(outputStream);
                outputStream.flush();
                outputStream.close();


            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    outputStream.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }



        return null;
    }

    //删
    @GetMapping("queryOneSongCard")
    public   Result<Object> queryOneSongCard(){

        return ResultUtil.ok();
    }

    @GetMapping("/test")
    public void test(){
        tagService.insertOneTag("hello");
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
       // songCardVO.setAudio_url(song.getAudio_url());

        return songCardVO;
    }
}
