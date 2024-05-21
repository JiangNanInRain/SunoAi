package com.jninrain.sunoai.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310DateTimeDeserializerBase;
import com.jninrain.sunoai.util.Http.HttpCommon;
import lombok.extern.slf4j.Slf4j;
import sun.security.provider.Sun;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装第三方SunoApi生成音乐接口
 *
 * @Auther: fei
 * @Date: 2024/05/21/14:03
 * @Description:
 */
@Slf4j
public class SunoApiUtil {
    public static  final  String host = "https://api.sunoaiapi.com/api/v1/gateway";
    public static  final  String ApiKey = "4QNpgiK1Y4eWGx4908FIP0ls8VL0hziP";

    /**(OK)
     * 查询歌词生成结果
     *
     * @param lid
     * @return
     */
    public static com.alibaba.fastjson.JSONObject queryLyrics(String lid){
        //请求参数
        Map<String ,String> parameters = new HashMap<>(1);

        //请求头
        Map<String ,String> head = new HashMap<>(1);
        head.put("api-key",ApiKey);

        com.alibaba.fastjson.JSONObject res = null;
        try{
            log.info("调用SunoAPi接口 ：https://api.sunoaiapi.com/api/v1/gateway/lyrics/{lid}");
            res = HttpCommon.getHttpRequestFastJson(host,"/lyrics/"+lid,parameters,head);
            log.info("返回结果: "+res.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }


        return res;
    }

    /**(OK)
     * 根据prompt生成歌词
     *
     * @param prompt 创作提示词
     * @return
     */
    public  static com.alibaba.fastjson.JSONObject generateLyricsByPrompt(String prompt){
        //请求参数
        Map<String ,String > parameters = new HashMap<>(1);

        //请求头
        Map<String ,String> head = new HashMap<>(1);
        head.put("api-key",ApiKey);

        //请求体
        JSONObject body = new JSONObject();
        body.put("prompt",prompt);

        com.alibaba.fastjson.JSONObject res = null;
        try{
            log.info("调用SunoAPi接口 ：https://api.sunoaiapi.com/api/v1/gateway/generate/lyrics");
            res = HttpCommon.postHttpRequestFastJson(host,"/generate/lyrics",parameters,head,body,null);
            log.info("返回结果: "+res.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }


        return res;

    }

    /**(OK)
     * 仅根据Prompt生成音乐，对应Custom Mode 关闭状态
     *
     * @param prompt            对音乐的描述
     * @param AbsoluteMusic     是否是纯音乐
     * @return
     */
    public static  com.alibaba.fastjson.JSONObject generateMusicOnlyByPrompt(String prompt,Boolean AbsoluteMusic){
        //请求参数
        Map<String ,String > parameters = new HashMap<>(1);

        //请求头
        Map<String ,String> head = new HashMap<>(1);
        head.put("api-key",ApiKey);

        //请求体
        JSONObject body = new JSONObject();
        body.put("gpt_description_prompt",prompt);
        body.put("make_instrumental",AbsoluteMusic);

        com.alibaba.fastjson.JSONObject res = null;
        try{
            log.info("调用SunoAPi接口 ：https://api.sunoaiapi.com/api/v1/gateway/generate/gpt_desc");
            res = HttpCommon.postHttpRequestFastJson(host,"/generate/gpt_desc",parameters,head,body,null);
            log.info("返回结果: "+res.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }


        return res;
    }

    /**(ok)
     *  查询歌曲生成结果（可一次查询多首曲子）
     * @param ids    查询的SongId列表
     * @return
     */
    public static com.alibaba.fastjson.JSONObject queryGenerateResult(String... ids){
        //请求参数
        Map<String ,String > parameters = new HashMap<>(1);
        for (String id:ids){
            parameters.put("id",id);
        }

        //请求头
        Map<String ,String> head = new HashMap<>(1);
        head.put("api-key",ApiKey);

        com.alibaba.fastjson.JSONObject res = null;
        try{
            log.info("调用SunoAPi接口 ：https://api.sunoaiapi.com/api/v1/gateway/query");
            res = HttpCommon.getHttpRequestFastJson(host,"/query",parameters,head);
            log.info("返回结果: "+res.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }


        return res;

    }

    /**（ok）
     * 生成音乐
     *
     * @param json 请求参数
     * @return
     */
    public static com.alibaba.fastjson.JSONObject generateMusic(JSONObject json){
        //请求参数
        Map<String ,String > parameters = new HashMap<>(1);

        //请求头
        Map<String ,String> head = new HashMap<>(1);
        head.put("api-key",ApiKey);

        //请求体
        JSONObject body = new JSONObject();
        body.put("title",json.getString("title")); //音乐标题
        body.put("tags",json.getString("tags"));   //音乐风格（英文逗号隔开）
        body.put("prompt",json.getString("prompt"));//音乐描述
        //非必要参数
        if(null!=json.getString("mv")){ //模型参数
            body.put("mv",json.getString("mv"));
        }
        if(null!=json.getInteger("continue_at")){//从第几秒开始继续创作
            body.put("continue_at",json.getString("continue_at"));
        }
        if(null!=json.getString("continue_clip_id")){ //需要继续创作的歌曲id
            body.put("continue_clip_id",json.getString("continue_clip_id"));
        }

        com.alibaba.fastjson.JSONObject res = null;
        try{
            log.info("调用SunoAPi接口 ：https://api.sunoaiapi.com/api/v1/gateway/generate/music");
            res = HttpCommon.postHttpRequestFastJson(host,"/generate/music",parameters,head,body,null);
            log.info("返回结果: "+res.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }

    /**(OK)
     *  查询账户剩余生成音乐额度
     *
     * @return
     */
    public static  com.alibaba.fastjson.JSONObject queryLimit(){
        //请求参数
        Map<String ,String > parameters = new HashMap<>(1);

        //请求头
        Map<String ,String> head = new HashMap<>(1);
        head.put("api-key",ApiKey);

        com.alibaba.fastjson.JSONObject res = null;
        try{
            log.info("调用SunoAPi接口 ：https://api.sunoaiapi.com/api/v1/gateway/limit");
            res = HttpCommon.getHttpRequestFastJson(host,"/limit",parameters,head);
            log.info("返回结果: "+res.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }

    /**
     * 音频合并接口
     *
     * @param clipId
     * @return
     */
    public static  com.alibaba.fastjson.JSONObject generateConcat(String clipId){
        //请求参数
        Map<String ,String > parameters = new HashMap<>(1);

        //请求头
        Map<String ,String> head = new HashMap<>(1);
        head.put("api-key",ApiKey);

        //请求体
        JSONObject body = new JSONObject();
        body.put("clip_id",clipId);

        com.alibaba.fastjson.JSONObject res = null;
        try{
            log.info("调用SunoAPi接口 ：https://api.sunoaiapi.com/api/v1/gateway/generate/concat");
            res = HttpCommon.postHttpRequestFastJson(host,"/generate/concat",parameters,head,body,null);
            log.info("返回结果: "+res.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }
    //测试工具类
    public static void main(String[] args) {
        JSONObject res = null;
        //res = SunoApiUtil.generateLyricsByPrompt("吉吉国王爱吃香蕉");
         //{"msg":"success","code":0,"data":{"id":"224c1649-d83c-4fcf-b901-d5ce9574ac76"}}

        //res = SunoApiUtil.queryLyrics("224c1649-d83c-4fcf-b901-d5ce9574ac76");
        //{"msg":"success","code":0,"data":{"user_id":"EXKEHcAISbQD1qOF9NhLdq0InM22","text":"[Verse]\n吉吉国王，心爱香蕉，\n一口咬下，满嘴黄金，\n甜甜的味道，让人陶醉，\n咔嚓一声，尽情享受。\n\n[Verse 2]\n大街小巷，吉吉欢蹦跳，\n手里香蕉，一副淘气嘴，\n嗅到香味，忍不住呼哧呼哧，\n大快朵颐，真是太美妙。\n\n[Chorus]\n香蕉，香蕉，黄皮黄心，\n吉吉国王最心仪的食物，\n食之无忌，快乐满满，\n一起来嚼上一口，香蕉的乐章。","id":36383,"title":"香蕉狂想曲","lyrics_id":"224c1649-d83c-4fcf-b901-d5ce9574ac76"}}

        //res = SunoApiUtil.queryLimit();
        //{"msg":"success","code":0,"data":{"songs_left":1000,"points":0}}

        //res = SunoApiUtil.generateMusicOnlyByPrompt("吉吉国王超级喜欢吃香蕉",false);
        //{"msg":"success","code":0,"data":[{"meta_prompt":"","title":"","song_id":"ca8ab5ca-d57a-4605-9b32-fafad1646314","video_url":"","model_name":"chirp-v3","user_id":"EXKEHcAISbQD1qOF9NhLdq0InM22","audio_url":"","status":"submitted"},{"meta_prompt":"","title":"","song_id":"2f47c9ba-2975-4ae7-884e-5b6690151da3","video_url":"","model_name":"chirp-v3","user_id":"EXKEHcAISbQD1qOF9NhLdq0InM22","audio_url":"","status":"submitted"}]}

        //res = SunoApiUtil.queryGenerateResult("ca8ab5ca-d57a-4605-9b32-fafad1646314","2f47c9ba-2975-4ae7-884e-5b6690151da3");
        //{"code":0,"msg":"success","data":[{"user_id":"EXKEHcAISbQD1qOF9NhLdq0InM22","song_id":"43bc3a75-2d03-4cb0-8bd0-47e0bf012114","status":"submitted","title":"","image_large_url":null,"image_url":null,"model_name":"chirp-v3","video_url":"","audio_url":"","meta_tags":null,"meta_prompt":"","meta_duration":null,"meta_error_msg":null,"meta_error_type":null},{"user_id":"EXKEHcAISbQD1qOF9NhLdq0InM22","song_id":"82b94364-97e0-40a1-b40b-28e4f0065ace","status":"submitted","title":"","image_large_url":null,"image_url":null,"model_name":"chirp-v3","video_url":"","audio_url":"","meta_tags":null,"meta_prompt":"","meta_duration":null,"meta_error_msg":null,"meta_error_type":null}]}

//        JSONObject json = new JSONObject();
//        json.put("title","吉吉国王和蜂蜜");
//        json.put("tags","民谣");
//        json.put("prompt","吉吉国王和毛毛抢蜂蜜");
//        res = SunoApiUtil.generateMusic(json);
        //{"msg":"success","code":0,"data":[{"meta_prompt":"吉吉国王和毛毛抢蜂蜜","title":"吉吉国王和蜂蜜","meta_tags":"民谣","song_id":"f193ff87-78b0-41d9-b280-2ae92e761c92","video_url":"","model_name":"chirp-v3","user_id":"EXKEHcAISbQD1qOF9NhLdq0InM22","audio_url":"","status":"submitted"},{"meta_prompt":"吉吉国王和毛毛抢蜂蜜","title":"吉吉国王和蜂蜜","meta_tags":"民谣","song_id":"448dae58-d3c4-4881-8871-abf528a48603","video_url":"","model_name":"chirp-v3","user_id":"EXKEHcAISbQD1qOF9NhLdq0InM22","audio_url":"","status":"submitted"}]}
        res = SunoApiUtil.generateConcat("dae58-d3c4-4881-8871-abf528a48603");
        System.out.println(res.toJSONString());
    }



}
