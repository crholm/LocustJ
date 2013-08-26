package se.rzz.locustj;

import se.rzz.locustj.anotations.Task;

/**
 * Created with IntelliJ IDEA.
 * User: raz
 * Date: 8/21/13
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */

public class MyTest extends Locust {

    int i = 0;


    int a = 0;
    @Task(name = "First", ratio = 10)
    public void aTask(){
        System.out.println("1 -> count: " + ++a + " totalcount: " + ++i);
    }

    int another = 0;
    @Task(name = "Second", ratio = 70)
    public void anotherTask(){
        System.out.println("2 -> count: " + ++another + " totalcount: " + ++i);
    }

    int b = 0;
    @Task(name = "third", ratio = 20, group = "hej")
    public void bTask(){
        System.out.println("3 -> count: " + ++b + " totalcount: " + ++i);
    }




    public static void main(String args[]){
        Spawner.spawn(new MyTest());

    }
}
