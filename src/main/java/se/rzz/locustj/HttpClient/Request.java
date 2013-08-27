package se.rzz.locustj.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: raz
 * Date: 8/27/13
 * Time: 7:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Request {

    private HttpMethod method;
    private String uri;
    private String host;
    private String protocol;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();
    private String body;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return protocol+"://" + host + uri;
    }

    public void setUrl(String url) {
        String parts[] = url.split("://", 2);


        if(parts.length < 2){
            protocol = "http";
            parts = parts[0].split("/", 2);
        }else{
            protocol = parts[0];
            parts = parts[1].split("/", 2);
        }

        host = parts[0];
        uri = "/" + parts[1];
    }


    public void addHeader(String key, String value){
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }


    public void addCookie(String key, String value){
        cookies.put(key, value);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void setBody(String body){
        this.body = body;
    }

    public String getBody(){
        return body;
    }



    public String toString(){
        final String NL = "\r\n";
        StringBuilder sb = new StringBuilder();

        sb.append(method).append(" ").append(uri).append(" ").append("HTTP/1.1").append(NL);

        sb.append("Host: ").append(host).append(NL);
        sb.append("Referer: ").append(getUrl());

        for(String key : headers.keySet()){
            sb.append(key).append(": ").append(headers.get(key)).append(NL);
        }

        if(cookies.size() > 0){
            sb.append("Cookie: ");
            for(String key : cookies.keySet()){
                sb.append(key).append("=").append(cookies.get(key)).append("; ");
            }
            sb.append(NL);
        }

        sb.append(NL).append(NL);

        if(body != null){
            sb.append(body);
        }


        return sb.toString();
    }
}
