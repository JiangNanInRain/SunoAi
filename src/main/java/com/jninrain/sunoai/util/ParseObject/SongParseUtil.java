package com.jninrain.sunoai.util.ParseObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jninrain.sunoai.entity.Song;
import com.jninrain.sunoai.util.SunoApiUtil;
import net.sf.json.JSONObject;
import org.apache.tomcat.websocket.WsIOException;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: fei
 * @Date: 2024/05/23/11:19
 * @Description:
 */
public class SongParseUtil {

    public static Song queryOneSong(String id){
        net.sf.json.JSONArray json   = SunoApiUtil.queryGenerateResult(id);

        JSONObject song = null;
        if (json != null) {
            song = (net.sf.json.JSONObject) json.get(0);
        }
        System.out.println(song.toString());
        Song ret = new Song();
        ret.setSong_id(song.getString("id"));
        ret.setUser_id(song.getString("user_id"));
        ret.setTitle(song.getString("title"));
        ret.setModel_name(song.getString("model_name"));
        ret.setCreated_time( song.getString("created_at"));
        ret.setTags(song.getJSONObject("meta_data").getString("tags"));
        ret.setStatus(song.getString("status"));
        ret.setVideo_url(song.getString("video_url"));
        ret.setAudio_url(song.getString("audio_url"));
        ret.setImage_url(song.getString("image_url"));
        ret.setImage_large_url(song.getString("image_large_url"));
        ret.setDuration(song.getJSONObject("meta_data").getDouble("duration"));

        System.out.println("duration:"+song.getJSONObject("meta_data").getString("duration"));
        ret.setLyrics(song.getJSONObject("meta_data").getString("prompt"));
        ret.setError_type(song.getJSONObject("meta_data").getString("error_type"));
        ret.setError_message(song.getJSONObject("meta_data").getString("error_message"));



        ret.setLyrics(song.getJSONObject("meta_data").getString("prompt"));

        return ret;
    }

    public static void main(String[] args) {
        System.out.println( queryOneSong("6fa98544-f570-4394-a621-0fd36b794f52").toString());
    }
}
