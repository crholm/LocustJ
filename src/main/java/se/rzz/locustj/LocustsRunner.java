package se.rzz.locustj;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: raz
 * Date: 8/27/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocustsRunner {

    private static boolean running;

    public static void stopAll(){
        running = false;
    }

    public static void restartAll(){
        running = true;
    }

    private List<Locust> locusts;

    public LocustsRunner(List<Locust> locusts){
        this.locusts = locusts;
        start();
    }

    private void start(){
        running = true;

        for(Locust locust : locusts){
            locust.init();
        }

        System.out.println("Hit");
        while(running){
            for(Locust locust : locusts){
                locust.runTick();


                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

    }



}
