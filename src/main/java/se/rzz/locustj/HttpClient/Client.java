package se.rzz.locustj.HttpClient;

/**
 * Created with IntelliJ IDEA.
 * User: raz
 * Date: 8/27/13
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    private String name;
    private final Request request = new Request();

    public Client(String name) {
        this.name = name;
    }

    private void setBaseRequest(String url, HttpMethod method){
        request.setUrl(url);
        request.setMethod(method);
    }

    public Request getRequest(){
        return request;
    }

    public Client post(String url){
        setBaseRequest(url, HttpMethod.POST);
        return this;
    }

    public Client put(String url){
        setBaseRequest(url, HttpMethod.PUT);
        return this;
    }

    public Client get(String url){
        setBaseRequest(url, HttpMethod.GET);
        return this;
    }

    public Client delete(String url){
        setBaseRequest(url, HttpMethod.DELETE);
        return this;
    }


    public Client header(String key, String value){
        request.addHeader(key, value);
        return this;
    }

    public Client cookie(String key, String value){
        request.addCookie(key, value);
        return this;
    }









}
