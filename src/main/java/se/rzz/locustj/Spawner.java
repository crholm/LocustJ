package se.rzz.locustj;

import se.rzz.locustj.anotations.Task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: raz
 * Date: 8/21/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class Spawner {

    int swarmSize = 100;



    public Spawner(Locust... locustArray){
        for(Locust locust : locustArray){

            new Thread(new LocustCloner(locust)).start();

        }


    }

    private class LocustCloner implements Runnable{
        Locust locust;

        private Map<String, TaskGroup> taskGroups = new HashMap<>();

        public LocustCloner(Locust locust){
            this.locust = locust;
        }


        private void createTaskGroups(){

            Method methods[] = locust.getClass().getMethods();

            for(Method method : methods){

                Task annotation = method.getAnnotation(Task.class);
                if(annotation != null){

                    TaskGroup taskGroup = taskGroups.get(annotation.group());

                    if(taskGroup == null){
                        taskGroup = new TaskGroup(annotation.group(), locust);
                        taskGroups.put(annotation.group(), taskGroup);
                    }

                    taskGroup.addTask(annotation.name(), annotation.ratio(), method);

                }
            }

        }


        @Override
        public void run() {





        }
    }




    public static void spawn(Locust... tasks){
        new Spawner(tasks);
    }
}
