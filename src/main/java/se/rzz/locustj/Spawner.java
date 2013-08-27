package se.rzz.locustj;

import se.rzz.locustj.anotations.Task;

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

    int swarmSize = 1;



    private Spawner(Locust... locustArray){
        for(Locust locust : locustArray){

            new Thread(new LocustCloner(locust)).start();

        }


    }

    private class LocustCloner implements Runnable{
        Locust locustPrototype;
        List<Locust> locustClones = new ArrayList<>();


        public LocustCloner(Locust locustPrototype){
            this.locustPrototype = locustPrototype;
        }


        private void createTaskGroups() throws CloneNotSupportedException {

            Method methods[] = locustPrototype.getClass().getMethods();
            Map<String, TaskGroup> taskGroups = new HashMap<>();

            for(Method method : methods){

                Task annotation = method.getAnnotation(Task.class);
                if(annotation != null){

                    TaskGroup taskGroup = taskGroups.get(annotation.group());

                    if(taskGroup == null){
                        taskGroup = new TaskGroup(annotation.group());
                        taskGroups.put(annotation.group(), taskGroup);
                    }

                    taskGroup.addTask(annotation.name(), annotation.ratio(), method);

                }
            }

            for(int i = 0; i < swarmSize; i++){
                Locust l = locustPrototype.deepClone();
                l.setTaskGroups(taskGroups);
                locustClones.add(l);
            }


        }


        @Override
        public void run() {
            try {
                createTaskGroups();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                System.exit(1);
            }

            new LocustsRunner(locustClones);


        }
    }





    public static void spawn(Locust... tasks){
        new Spawner(tasks);
    }
}
