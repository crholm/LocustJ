package se.rzz.locustj.HttpClient;

import java.util.Observable;

/**
 * Created with IntelliJ IDEA.
 * User: raz
 * Date: 8/27/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Http extends Observable{

    private static final int THREADS = Runtime.getRuntime().availableProcessors();

    private static class SingletonHolder {
        public static final Http INSTANCE = new Http();
    }

    public static Http instance() {
        return SingletonHolder.INSTANCE;
    }


    private Http(){

    }

    public static Client client(){
        return client("default");
    }

    public static Client client(String name){
        return new Client(name);
    }





}
