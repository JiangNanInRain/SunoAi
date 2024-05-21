package com.jninrain.sunoai.util.Http;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONStringer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http访问的共有组件 2.0
 *
 * @author 顾继钞
 * @date 2018年8月23日 上午11:26:05
 */

@Slf4j
public class HttpCommon {

    /**返回值**/
    private final static String CODE = "code";

    /**出错**/
    private final static String ERROR = "error";

    /**错误信息**/
    private final static String EXCEPTION_MESSAGE = "exceptionMessage";

    /**
     * http请求获取
     * @author 顾继钞
     * @date 2018年9月19日 下午7:07:33
     * @param httpUriRequest
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static JSONObject doHttpRequest(HttpUriRequest httpUriRequest) throws ClientProtocolException, IOException {
		JSONObject json = new JSONObject();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(httpUriRequest);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			String result = IOUtils.toString(instream, "UTF-8");
			log.info("result：" + result);
			json = JSONObject.fromObject(result);
		} else {
			json.put("code", 0);
		}
		httpclient.close();
		response.close();
		return json;
	}
    
   /**
    * http的uri构建
    * @author 顾继钞
    * @date 2018年9月19日 下午7:08:30
    * @param scheme 
    * @param host 域名
    * @param path 接口地址
    * @param parameters 参数
    * @return
    * @throws ClientProtocolException
    * @throws IOException
    * @throws URISyntaxException
    */
	public static URI getURI(String scheme, String host, String path, List<NameValuePair> parameters)
			throws ClientProtocolException, IOException, URISyntaxException {
		URI	uri = new URIBuilder().setScheme(scheme).setHost(host).setPath(path).addParameters(parameters)
					.build();
		return uri;
	}
    public static JSONArray getHttpRequestJSONArray (String url, String path, Map<String, String> parameters,
                                                     Map<String, String> heards) throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[GET]"+uri.build());
        HttpGet httpGet = new HttpGet(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpGet.setHeader(key, value);
            }
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        JSONArray json = new JSONArray();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                json = JSONArray.fromObject(result);
            }

            response.close();
        } catch (Exception e) {
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[GET]"+json);
        return json;
    }

    /**
     * httpGET请求
     *
     * @param url        访问地址 (http://或者 https://)
     * @param path       接口路径
     * @param parameters 参数(Map传参)
     * @param heads     头文件(Map传参)
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     * @author 顾继钞
     * @date 2018年9月11日 上午9:02:20
     */
    public static JSONObject getHttpRequest(String url, String path, Map<String, String> parameters,
                                            Map<String, String> heads) throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[GET]"+uri.build());
        HttpGet httpGet = new HttpGet(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heads) {
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpGet.setHeader(key, value);
            }
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject json = new JSONObject();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpClient.close();
            return json;
        }
        httpClient.close();
        log.info("http访问接口返回值[GET]"+json);
        return json;
    }

    public static JSONObject postWithParamsForString(String url, List<NameValuePair> params){
        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject json = new JSONObject();
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
            HttpResponse response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode==200){
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                json = JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * httpPOST请求
     *
     * @param url        访问地址 (http:// 或者 https://)
     * @param path       接口路径
     * @param parameters 参数(Map传参)
     * @param heards     头文件(Map传参)
     * @param jsonObject 传递数据(二选一)
     * @param map        传递数据(二选一)
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     * @author 顾继钞
     * @date 2018年9月11日 上午9:08:45
     */
    public static JSONObject postHttpRequest(String url, String path, Map<String, String> parameters,
                                             Map<String, String> heards, JSONObject jsonObject, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);

        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]"+uri.build());
        HttpPost httpPost = new HttpPost(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
        	 if (null != jsonObject) {
        		 heards.put("Content-Type","application/json");
        	 }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        if (null != jsonObject) {
            httpPost = getJsonHttpPostJSON(httpPost, jsonObject);
        } else if (null != map) {
            httpPost = getJsonHttpPostMap(httpPost, map);
        }
        JSONObject json = new JSONObject();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]"+json);
        return json;
    }

    public static JSONArray postHttpRequestReturnArray(String url, String path, Map<String, String> parameters,
                                             Map<String, String> heards, JSONObject jsonObject, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]"+uri.build());
        HttpPost httpPost = new HttpPost(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            if (null != jsonObject) {
                heards.put("Content-Type","application/json");
            }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        if (null != jsonObject) {
            httpPost = getJsonHttpPostJSON(httpPost, jsonObject);
        } else if (null != map) {
            httpPost = getJsonHttpPostMap(httpPost, map);
        }
        JSONArray json = new JSONArray();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                log.info("result：" + result);
                json = JSONArray.fromObject(result);
            }
            response.close();
        } catch (Exception e) {

            httpclient.close();
            return json;
        }
        httpclient.close();
        return json;
    }


    public static JSONObject postHttpRequestArray(String url, String path, Map<String, String> parameters,
                                             Map<String, String> heards, JSONArray jsonArray, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]"+uri.build());
        HttpPost httpPost = new HttpPost(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        if (null != jsonArray) {
            httpPost = getJsonHttpPostJSON(httpPost, jsonArray);
        } else if (null != map) {
            httpPost = getJsonHttpPostMap(httpPost, map);
        }
        JSONObject json = new JSONObject();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]"+json);
        return json;
    }

    /**
     * 模拟post传递json格式数据(map格式)
     *
     * @param httpPost
     * @param values
     * @return
     * @throws UnsupportedEncodingException
     * @throws JSONException
     * @author 顾继钞
     * @date 2018年8月23日 上午9:07:23
     */
    public static HttpPost getJsonHttpPostMap(HttpPost httpPost, Map<String, String> values)
            throws JSONException {
        String jsonString;
        // 生成一个JSON格式的字符串，将http全部请求参数设置到httpPost的Entity中
        if (values != null) {
            JSONStringer jsonStringer = (JSONStringer) new JSONStringer().object();
            // 以Set方式遍历Map数据，设置请求参数
            for (Map.Entry<String, String> oneEntry : values.entrySet()) {
                String key = oneEntry.getKey();
                String value = oneEntry.getValue();
                jsonStringer.key(key).value(value);
            }
            jsonStringer.endObject();
            // 将JSON的格式的请求参数列表，转化为字符串格式
            jsonString = jsonStringer.toString();
            // 将该字符串设置为HttpEntity，并设置编码方式
            HttpEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);
        }
        return httpPost;
    }

    /**
     * 模拟post传递json格式数据(json格式)
     *
     * @param httpPost
     * @param values
     * @return
     * @throws UnsupportedEncodingException
     * @throws JSONException
     * @author 顾继钞
     * @date 2018年8月23日 上午8:59:35
     */
    public static HttpPost getJsonHttpPostJSON(HttpPost httpPost, JSONObject values)
            throws JSONException {
        String jsonString;
        if (null != values) {
            jsonString = values.toString();
            // 将该字符串设置为HttpEntity，并设置编码方式
            HttpEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);
        }
        return httpPost;
    }

    public static HttpPost getJsonHttpPostJSON(HttpPost httpPost, JSONArray jsonArray)
            throws JSONException {
        String jsonString;
        if (null != jsonArray) {
            jsonString = jsonArray.toString();
            // 将该字符串设置为HttpEntity，并设置编码方式
            HttpEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);
        }
        return httpPost;
    }


    /**
     * 模拟put传递json格式数据(json格式)
     *
     * @param httpPut
     * @param values
     * @return
     * @throws UnsupportedEncodingException
     * @throws JSONException
     * @author 顾继钞
     * @date 2018年8月23日 上午8:59:35
     */
    public static HttpPut getJsonHttpPutJSON(HttpPut httpPut, JSONObject values)
            throws JSONException {
        String jsonString;
        if (null != values) {
            jsonString = values.toString();
            // 将该字符串设置为HttpEntity，并设置编码方式
            HttpEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPut.setEntity(entity);
        }
        return httpPut;
    }

    public static HttpPut getJsonHttpPutJSON(HttpPut httpPut, JSONArray jsonArray)
            throws JSONException {
        String jsonString;
        if (null != jsonArray) {
            jsonString = jsonArray.toString();
            // 将该字符串设置为HttpEntity，并设置编码方式
            HttpEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPut.setEntity(entity);
        }
        return httpPut;
    }

    /**
     * 模拟put传递json格式数据(map格式)
     *
     * @param httpPut
     * @param values
     * @return
     * @throws UnsupportedEncodingException
     * @throws JSONException
     * @author 顾继钞
     * @date 2018年8月23日 上午9:07:23
     */
    public static HttpPut getJsonHttpPutMap(HttpPut httpPut, Map<String, String> values)
            throws JSONException {
        String jsonString;
        // 生成一个JSON格式的字符串，将http全部请求参数设置到httpPost的Entity中
        if (values != null) {
            JSONStringer jsonStringer = (JSONStringer) new JSONStringer().object();
            // 以Set方式遍历Map数据，设置请求参数
            for (Map.Entry<String, String> oneEntry : values.entrySet()) {
                String key = oneEntry.getKey();
                String value = oneEntry.getValue();
                jsonStringer.key(key).value(value);
            }
            jsonStringer.endObject();
            // 将JSON的格式的请求参数列表，转化为字符串格式
            jsonString = jsonStringer.toString();
            // 将该字符串设置为HttpEntity，并设置编码方式
            HttpEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPut.setEntity(entity);
        }
        return httpPut;
    }


    /**
     * 模拟delete传递json格式数据(json格式)
     *
     * @param httpDelete
     * @param values
     * @return
     * @throws UnsupportedEncodingException
     * @throws JSONException
     * @author 顾继钞
     * @date 2018年8月23日 上午8:59:35
     */
    public static MyHttpDelete getJsonHttpDeleteJSON(MyHttpDelete httpDelete, JSONObject values)
            throws JSONException {
        String jsonString;
        if (null != values) {
            jsonString = values.toString();
            // 将该字符串设置为HttpEntity，并设置编码方式
            HttpEntity entity = new StringEntity(jsonString, "UTF-8");
            httpDelete.setEntity(entity);
        }
        return httpDelete;
    }

    public static MyHttpDelete getJsonHttpDeleteJSON(MyHttpDelete httpDelete, JSONArray jsonArray)
            throws JSONException {
        String jsonString;
        if (null != jsonArray) {
            jsonString = jsonArray.toString();
            // 将该字符串设置为HttpEntity，并设置编码方式
            HttpEntity entity = new StringEntity(jsonString, "UTF-8");
            httpDelete.setEntity(entity);
        }
        return httpDelete;
    }

    /**
     * 模拟delete传递json格式数据(map格式)
     *
     * @param httpDelete
     * @param values
     * @return
     * @throws UnsupportedEncodingException
     * @throws JSONException
     * @author 顾继钞
     * @date 2018年8月23日 上午9:07:23
     */
    public static MyHttpDelete getJsonHttpDeleteMap(MyHttpDelete httpDelete, Map<String, String> values)
            throws JSONException {
        String jsonString;
        // 生成一个JSON格式的字符串，将http全部请求参数设置到httpPost的Entity中
        if (values != null) {
            JSONStringer jsonStringer = (JSONStringer) new JSONStringer().object();
            // 以Set方式遍历Map数据，设置请求参数
            for (Map.Entry<String, String> oneEntry : values.entrySet()) {
                String key = oneEntry.getKey();
                String value = oneEntry.getValue();
                jsonStringer.key(key).value(value);
            }
            jsonStringer.endObject();
            // 将JSON的格式的请求参数列表，转化为字符串格式
            jsonString = jsonStringer.toString();
            // 将该字符串设置为HttpEntity，并设置编码方式
            HttpEntity entity = new StringEntity(jsonString, "UTF-8");
            httpDelete.setEntity(entity);
        }
        return httpDelete;
    }

    /**
     * 填充参数
     *
     * @param uri
     * @param parameters
     * @描述
     * @返回值 org.apache.http.client.utils.URIBuilder
     * @创建人 顾继钞
     * @创建时间 2018/9/11 10:12
     */

    public static URIBuilder fillUri(URIBuilder uri, Map<String, String> parameters)
            throws JSONException {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value =  entry.getValue();
            uri.addParameter(key, value);
        }
        return uri;
    }
    
    /**
     * 
     *
     *功能说明：post方式提交json字符串
     *
     * @author xuren
     *
     * createTime: 2019年3月5日
     * @param url 
     * @param jsonStr
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static JSONObject sendJsonHttpPost(String url, String jsonStr)  throws ClientProtocolException, IOException, URISyntaxException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        JSONObject json = new JSONObject();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            ContentType contentType = ContentType.create("application/json", CharsetUtils.get("UTF-8"));
            httpPost.setEntity(new StringEntity(jsonStr, contentType));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return json;
    }


    public static JSONObject postFormData(String url, String path, Map<String, String> parameters,
                                          Map<String, String> heards, JSONObject jsonObject, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]" + uri.build());
        HttpPost httpPost = new HttpPost(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            if (null != jsonObject) {
                heards.put("Content-Type", "application/json");
            }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        if (null != jsonObject) {
            httpPost = getJsonHttpPostJSON(httpPost, jsonObject);
        }
        if(null != map) {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                BasicNameValuePair basicNameValuePair = new BasicNameValuePair(key, value);
                list.add(basicNameValuePair);
            }
            // 第二步：我们发现Entity是一个接口，所以只能找实现类，发现实现类又需要一个集合，集合的泛型是NameValuePair类型
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list);
            // 第一步：通过setEntity 将我们的entity对象传递过去
            httpPost.setEntity(formEntity);
        }
        JSONObject json = new JSONObject();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]" + json);
        return json;
    }

    /**
     * httpDelete方法
     * @param url
     * @param path
     * @param parameters
     * @param heards
     * @param jsonObject
     * @return net.sf.json.JSONObject
     * @author xulf
     * @date 2021/7/6 11:28
     **/
    public static JSONObject deleteHttpRequest(String url, String path, Map<String, String> parameters,
                                             Map<String, String> heards, JSONObject jsonObject)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }

        HttpDelete httpDelete = new HttpDelete(uri.build());
        log.info("http访问接口[POST]{},{}",uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            if (null != jsonObject) {
                heards.put("Content-Type","application/json");
            }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpDelete.setHeader(key, value);
            }
        }

        JSONObject json = new JSONObject();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]"+json);
        return json;
    }

    /**
     * httpPut方法
     * @param url
     * @param path
     * @param parameters
     * @param heards
     * @param jsonObject
     * @param map
     * @return net.sf.json.JSONObject
     * @author xulf
     * @date 2021/7/6 11:29
     **/
    public static JSONObject putHttpRequest(String url, String path, Map<String, String> parameters,
                                             Map<String, String> heards, JSONObject jsonObject, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]"+uri.build());
        HttpPost httpPost = new HttpPost(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            if (null != jsonObject) {
                heards.put("Content-Type","application/json");
            }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        if (null != jsonObject) {
            httpPost = getJsonHttpPostJSON(httpPost, jsonObject);
        } else if (null != map) {
            httpPost = getJsonHttpPostMap(httpPost, map);
        }
        JSONObject json = new JSONObject();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]"+json);
        return json;
    }

    /**
     * @Author: xuren
     * @Description:    忽略证书 post
     * @Param: url
     * @Param: path
     * @Param: parameters
     * @Param: heards
     * @Param: jsonObject
     * @Param: map
     * @Date: 2022-03-23
     */
    public static JSONObject postHttpRequestIgnoreCer(String url, String path, Map<String, String> parameters,
                                                      Map<String, String> heards, JSONObject jsonObject, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]"+uri.build());
        HttpPost httpPost = new HttpPost(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            if (null != jsonObject) {
                heards.put("Content-Type","application/json");
            }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        if (null != jsonObject) {
            httpPost = getJsonHttpPostJSON(httpPost, jsonObject);
        } else if (null != map) {
            httpPost = getJsonHttpPostMap(httpPost, map);
        }
        JSONObject json = new JSONObject();
        CloseableHttpClient httpclient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]"+json);
        return json;
    }

    /**
     * @Author: xuren
     * @Description:    忽略证书 post（fastJson）
     * @Param: url
     * @Param: path
     * @Param: parameters
     * @Param: heards
     * @Param: jsonObject
     * @Param: map
     * @Date: 2022-03-23
     */
    public static com.alibaba.fastjson.JSONObject postFastJsonHttpRequestIgnoreCer(String url, String path, Map<String, String> parameters,
                                                                                   Map<String, String> heards, com.alibaba.fastjson.JSONObject jsonObject, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]"+uri.build());
        HttpPost httpPost = new HttpPost(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            if (null != jsonObject) {
                heards.put("Content-Type","application/json");
            }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        if (null != jsonObject) {
            httpPost = getJsonHttpPostFastJSON(httpPost, jsonObject);
        } else if (null != map) {
            httpPost = getJsonHttpPostMap(httpPost, map);
        }
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        CloseableHttpClient httpclient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = com.alibaba.fastjson.JSONObject.parseObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]"+json);
        return json;
    }

    /**
     * @Author: xuren
     * @Description:    忽略证书 post
     * @Param: url
     * @Param: path
     * @Param: parameters
     * @Param: heards
     * @Param: jsonObject
     * @Param: map
     * @Date: 2022-03-23
     */
    public static JSONObject postHttpRequestJsonArrayIgnoreCer(String url, String path, Map<String, String> parameters,
                                                      Map<String, String> heards, JSONArray jsonObject, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]"+uri.build());
        HttpPost httpPost = new HttpPost(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            if (null != jsonObject) {
                heards.put("Content-Type","application/json");
            }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        if (null != jsonObject) {
            httpPost = getJsonHttpPostJSON(httpPost, jsonObject);
        } else if (null != map) {
            httpPost = getJsonHttpPostMap(httpPost, map);
        }
        JSONObject json = new JSONObject();
        CloseableHttpClient httpclient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]"+json);
        return json;
    }

    /**
     * @Author: xuren
     * @Description:    忽略证书 get
     * @Param: url
     * @Param: path
     * @Param: parameters
     * @Param: heards
     * @Date: 2022-03-23
     */
    public static JSONObject getHttpRequestIgnoreCer(String url, String path, Map<String, String> parameters,
                                                     Map<String, String> heards) throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[GET]"+uri.build());
        HttpGet httpGet = new HttpGet(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpGet.setHeader(key, value);
            }
        }
        CloseableHttpClient httpclient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
        JSONObject json = new JSONObject();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[GET]"+json);
        return json;
    }


    /**
     * @Author: xuren
     * @Description:    忽略证书 get(fastJson)
     * @Param: url
     * @Param: path
     * @Param: parameters
     * @Param: heards
     * @Date: 2022-03-23
     */
    public static com.alibaba.fastjson.JSONObject getHttpRequestFastJsonIgnoreCer(String url, String path, Map<String, String> parameters,
                                                                                  Map<String, String> heards) throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[GET]"+uri.build());
        HttpGet httpGet = new HttpGet(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpGet.setHeader(key, value);
            }
        }
        CloseableHttpClient httpclient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                json = com.alibaba.fastjson.JSONObject.parseObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[GET]"+json);
        return json;
    }

    /**
     * httpPut方法  忽略证书
     * @param url
     * @param path
     * @param parameters
     * @param heards
     * @param jsonObject
     * @param map
     * @return net.sf.json.JSONObject
     * @author xulf
     * @date 2021/7/6 11:29
     **/
    public static JSONObject putHttpRequestIgnoreCer(String url, String path, Map<String, String> parameters,
                                            Map<String, String> heards, JSONObject jsonObject, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]"+uri.build());
//        HttpPost httpPost = new HttpPost(uri.build());
        HttpPut httpPut = new HttpPut(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            if (null != jsonObject) {
                heards.put("Content-Type","application/json");
            }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPut.setHeader(key, value);
            }
        }
        if (null != jsonObject) {
            httpPut = getJsonHttpPutJSON(httpPut, jsonObject);
        } else if (null != map) {
            httpPut = getJsonHttpPutMap(httpPut, map);
        }
        JSONObject json = new JSONObject();
        CloseableHttpClient httpclient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPut);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]"+json);
        return json;
    }

    /**
     * httpDelete方法  忽略证书
     * @param url
     * @param path
     * @param parameters
     * @param heards
     * @param jsonObject
     * @param map
     * @return net.sf.json.JSONObject
     * @author xulf
     * @date 2021/7/6 11:29
     **/
    public static JSONObject deleteHttpRequestIgnoreCer(String url, String path, Map<String, String> parameters,
                                                     Map<String, String> heards, JSONObject jsonObject, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]"+uri.build());
//        HttpDelete httpDelete = new HttpDelete(uri.build());
        MyHttpDelete httpDelete = new MyHttpDelete(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            if (null != jsonObject) {
                heards.put("Content-Type","application/json");
            }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpDelete.setHeader(key, value);
            }
        }
        if (null != jsonObject) {
            httpDelete = getJsonHttpDeleteJSON(httpDelete, jsonObject);
        } else if (null != map) {
            httpDelete = getJsonHttpDeleteMap(httpDelete, map);
        }
        JSONObject json = new JSONObject();
        CloseableHttpClient httpclient = (CloseableHttpClient) SkipHttpsUtil.wrapClient();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = JSONObject.fromObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]"+json);
        return json;
    }

    /**
     * httpPOST请求(fastJson)
     *
     * @param url        访问地址 (http:// 或者 https://)
     * @param path       接口路径
     * @param parameters 参数(Map传参)
     * @param heards     头文件(Map传参)
     * @param jsonObject 传递数据(二选一)
     * @param map        传递数据(二选一)
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     * @author 顾继钞
     * @date 2018年9月11日 上午9:08:45
     */
    public static com.alibaba.fastjson.JSONObject postHttpRequestFastJson(String url, String path, Map<String, String> parameters,
                                                                          Map<String, String> heards, com.alibaba.fastjson.JSONObject jsonObject, Map<String, String> map)
            throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[POST]"+uri.build());
        HttpPost httpPost = new HttpPost(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heards) {
            if (null != jsonObject) {
                heards.put("Content-Type","application/json");
            }
            for (Map.Entry<String, String> entry : heards.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.setHeader(key, value);
            }
        }
        if (null != jsonObject) {
            httpPost = getJsonHttpPostFastJSON(httpPost, jsonObject);
        } else if (null != map) {
            httpPost = getJsonHttpPostMap(httpPost, map);
        }
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                System.out.println("result：" + result);
                json = com.alibaba.fastjson.JSONObject.parseObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpclient.close();
            return json;
        }
        httpclient.close();
        log.info("http访问接口返回值[POST]"+json);
        return json;
    }

    /**
     * 模拟post传递json格式数据(json格式)
     *
     * @param httpPost
     * @param values
     * @return
     * @throws UnsupportedEncodingException
     * @throws JSONException
     * @author 顾继钞
     * @date 2018年8月23日 上午8:59:35
     */
    public static HttpPost getJsonHttpPostFastJSON(HttpPost httpPost, com.alibaba.fastjson.JSONObject values)
            throws JSONException {
        String jsonString;
        if (null != values) {
            jsonString = values.toString();
            // 将该字符串设置为HttpEntity，并设置编码方式
            HttpEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);
        }
        return httpPost;
    }

    /**
     * httpGET请求(fastJson)
     *
     * @param url        访问地址 (http://或者 https://)
     * @param path       接口路径
     * @param parameters 参数(Map传参)
     * @param heads     头文件(Map传参)
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws URISyntaxException
     * @author 顾继钞
     * @date 2018年9月11日 上午9:02:20
     */
    public static com.alibaba.fastjson.JSONObject getHttpRequestFastJson(String url, String path, Map<String, String> parameters,
                                                                         Map<String, String> heads) throws ClientProtocolException, IOException, URISyntaxException {
        String[] urls = url.split("://");
        URIBuilder uri = new URIBuilder().setScheme(urls[0]).setHost(urls[1] + path);
        // 设置url传参
        if (null != parameters) {
            fillUri(uri, parameters);
        }
        log.info("http访问接口[GET]"+uri.build());
        HttpGet httpGet = new HttpGet(uri.build());
        // 设置头文件,给httpPost添加http请求头部：包含Content-Type、Accept、source、COOKIE信息
        if (null != heads) {
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpGet.setHeader(key, value);
            }
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                String result = IOUtils.toString(instream, "UTF-8");
                json = com.alibaba.fastjson.JSONObject.parseObject(result);
            }
            if (json.containsKey(ERROR)) {
                json.put(CODE, 0);
            }
            response.close();
        } catch (Exception e) {
            json.put(CODE, 0);
            json.put(EXCEPTION_MESSAGE, e.getMessage());
            httpClient.close();
            return json;
        }
        httpClient.close();
        log.info("http访问接口返回值[GET]"+json);
        return json;
    }
}