package se.rzz.locustj;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: raz
 * Date: 8/22/13
 * Time: 8:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class TaskGroup {


    private class RatioTask{
        int ratio;
        Method method;
        double tmpScore;
    }

    private Locust parrentToMethods;
    private Map<String, Method> tasks = new HashMap<>();
    private List<RatioTask> tasksList = new ArrayList<>();

    int totalRatio = 0;

    private String name;

    public TaskGroup(String name, Locust parrentToMethods){
        this.name = name;
        this.parrentToMethods = parrentToMethods;
    }

    public String getName(){
        return name;
    }

    public void addTask(String taskName, int ratio, Method method){
        totalRatio += ratio;
        tasks.put(taskName, method);

        RatioTask r = new RatioTask();
        r.method = method;
        r.ratio = ratio;
        tasksList.add(r);
    }

    public void run(String methodName){
        try {
            tasks.get(methodName).invoke(parrentToMethods);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
    }

    public void runNext(){


        double currentBestScore = -1;
        Method currentBestMethod = null;


        for(RatioTask r : tasksList){
            r.tmpScore = r.ratio;
            for(int i = 0; i < tasksList.size(); i++){
                r.tmpScore *= Math.random();
            }
            if(r.tmpScore > currentBestScore){
                currentBestScore = r.tmpScore;
                currentBestMethod = r.method;
            }
        }

        try {
            currentBestMethod.invoke(parrentToMethods);
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }

    }





}
