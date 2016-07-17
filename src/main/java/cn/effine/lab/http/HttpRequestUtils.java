/**
 * @author  effine
 * @email   iballader#gmail.com
 * @date    Jul 6, 2016  10:38:52 PM
 * @site    http://effine.cn
 * @since   1.0
 */

package cn.effine.lab.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class HttpRequestUtils {
	/** 请求服务器前缀url */
    private static final String PREFIX_URL = "http://localhost:8081";
    /** 编码格式*/
    private static final String ENCODING = "UTF-8";
    
    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    /**
     * get请求处理方法
     *
     * @author effine
     *
     * @param request
     *            [javax.servlet.http.HttpServletRequest] 请求Request
     * @param url
     *            请求地址；参数为restful的query类型(请求url不包含可变参数)即为null
     * @param param
     *            url参数
     * @return Map(包含两个key：Http状态码httpCode；方法返回值responseBody)
     *
     * @throws IOException
     * @throws HttpException
     */
    public static Map<String, Object> get(HttpServletRequest request, String url) throws HttpException, IOException{
        if(null == url)
            url = request.getRequestURI();
        HttpGet method = new HttpGet(PREFIX_URL + url);
        return  passHeaderAndParam(request, method);
    }

    /**
     * post请求处理方法
     *
     * @author effine
     *
     * @param request
     *            [javax.servlet.http.HttpServletRequest] 请求Request
     * @param url
     *            请求路径(如果为空则通过request获取)
     * @return Map(包含两个key：Http状态码httpCode；方法返回值responseBody)
     *
     * @throws IOException
     * @throws HttpException
     */
    public static Map<String, Object> post(HttpServletRequest request, String url) throws HttpException, IOException{
        if(null == url)
            url = request.getRequestURI();
        HttpRequestBase method = new HttpPost(PREFIX_URL + url);
        return  passHeaderAndParam(request, method);
    }

    /**
     * put请求处理方法
     *
     * @author effine
     *
     * @param request
     *            [javax.servlet.http.HttpServletRequest] 请求Request
     * @param url
     *            请求路径(如果为空则通过request获取)
     * @return Map(包含两个key：Http状态码httpCode；方法返回值responseBody)
     *
     * @throws IOException
     * @throws HttpException
     */
    public static Map<String, Object> put(HttpServletRequest request, String url) throws HttpException, IOException{
        if(null == url)
            url = request.getRequestURI();
        HttpRequestBase method = new HttpPut(PREFIX_URL + url);
        return  passHeaderAndParam(request, method);
    }

    /**
     * delete 请求处理方法
     *
     * @author effine
     *
     * @param request
     *            [javax.servlet.http.HttpServletRequest] 请求Request
     * @param url
     *            请求路径(如果为空则通过request获取)
     * @return Map(包含两个key：Http状态码httpCode；方法返回值responseBody)
     *
     * @throws IOException
     * @throws HttpException
     */
    public static Map<String, Object> delete(HttpServletRequest request, String url) throws HttpException, IOException{
        if(null == url)
            url = request.getRequestURI();
        HttpRequestBase method = new HttpDelete(PREFIX_URL + url);
        return  passHeaderAndParam(request, method);
    }

    /**
     * 操作Apache HttpRequest工具方法，并返回处理结果
     *
     * @author effine
     *
     * @param request
     *            [javax.servlet.http.HttpServletRequest]
     * @param method
     *            Apache HttpMethod
     * @return Map(包含两个key：Http状态码httpCode；方法返回值responseBody)
     *
     * @throws IOException
     * @throws HttpException
     */
    private static Map<String, Object> passHeaderAndParam(HttpServletRequest request, HttpRequestBase method) throws HttpException, IOException{
        // 传递Header
        Enumeration<String> headerRnum = request.getHeaderNames();
        while(headerRnum.hasMoreElements()){
            String key =  headerRnum.nextElement();
            method.setHeader(key, request.getHeader(key));
        }
        method.setHeader("apiVersion", "2.0");

        // 传递参数Param
        List<NameValuePair> params = new ArrayList<>();
        Enumeration<String> paramEnum = request.getParameterNames();
        while(paramEnum.hasMoreElements()){
            String key =  paramEnum.nextElement();
            params.add(new BasicNameValuePair(key, request.getParameter(key)));
        }

        // 处理POST方法的参数封装
        if(method instanceof HttpPost){
        	UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, ENCODING);
        	((HttpPost) method).setEntity(entity);
        }
        
        CloseableHttpResponse httpResponse = httpClient.execute(method);

        Map<String, Object> map = new HashMap<>();
        map.put("httpCode", httpResponse.getStatusLine());
        map.put("responseBody", httpResponse.getEntity().toString());
        method.releaseConnection();
        return map;
    }
}