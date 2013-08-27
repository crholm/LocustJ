package se.rzz.locustj;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: raz
 * Date: 8/21/13
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Locust implements Serializable {

    private Map<String, TaskGroup> taskGroups = new HashMap<>();

    private Stack<TaskGroup> groupStack = new Stack<>();
    private TaskGroup currentGroup;
    private Method currentTask;

    private long nextTickStart;

    public Map<String, TaskGroup> getTaskGroups() {
        return taskGroups;
    }

    public void setTaskGroups(Map<String, TaskGroup> taskGroups) {
        this.taskGroups = taskGroups;
    }

    void init(){
        currentGroup = taskGroups.get("default");
        if(currentGroup == null){
            currentGroup = taskGroups.values().iterator().next();
        }
        runNext();
        setNextTickStart();
    }

    boolean runTick(){
        if(System.currentTimeMillis() < nextTickStart){
            return false;
        }

        Method task = currentTask;

        try {
            task.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if(task == currentTask){
            runNext();
        }
        setNextTickStart();
        return true;

    }


    void setNextTickStart(){
        nextTickStart = System.currentTimeMillis() + (long)(Math.random()*(maxDelay()-minDelay())) + minDelay();
    }

    public void runNext(){
        currentTask = currentGroup.getNextTask();
    }

    public void runGroup(String groupName){
        groupStack.push(currentGroup);
        currentGroup = taskGroups.get(groupName);
        runNext();
    }

    public void runLastGroup(){
        currentGroup = groupStack.pop();
        runNext();
    }

    public void runTask(String taskName){
        currentTask = currentGroup.getTask(taskName);

        if(currentTask == null){
            for(TaskGroup group : taskGroups.values()){
                if(group.getTask(taskName) != null){
                    groupStack.push(currentGroup);
                    currentGroup = group;
                    currentTask = currentGroup.getTask(taskName);
                    break;
                }
            }
        }


    }

    public abstract long minDelay();
    public abstract long maxDelay();


    Locust deepClone() throws CloneNotSupportedException {
        Locust obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            obj = (Locust)in.readObject();
        }
        catch(IOException e) {
            e.printStackTrace();
            throw new CloneNotSupportedException();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            throw new CloneNotSupportedException();
        }
        return obj;
    }


}
