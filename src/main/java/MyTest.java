import se.rzz.locustj.Locust;
import se.rzz.locustj.Spawner;
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
        System.out.println("G default\tT1");
        runTask("third");
    }

    int another = 0;
    @Task(name = "Second", ratio = 70)
    public void anotherTask(){
        System.out.println("G default\tT2");
    }

    int b = 0;
    @Task(name = "third", ratio = 20, group = "hej")
    public void bTask(){
        System.out.println("G Hej\t\tT1");
        super.runLastGroup();
    }




    public static void main(String args[]){

        String url = "http://www.w3.org/Protocols/rfc1341/7_2_Multipart.html";
        String[] parts = url.split("://", 2);
        String protocol = parts[0];

        System.out.println(protocol);
        parts = parts[1].split("/", 2);

        String host = parts[0];
        System.out.println(host);
        String uri = "/" + parts[1];
        System.out.println(uri);

        String s = "1 2 ";

        StringBuilder buffer = new StringBuilder("1234");

        System.out.println(buffer.toString());
        System.out.println("len: " + s.split(" ").length);
        System.out.println("index: " + s.indexOf("2", 3) );

        for(String k : s.split(" ")){
            System.out.println(">" + k + "<");
        }











        Spawner.spawn(new MyTest());

    }

    @Override
    public long minDelay() {
        return 1000;
    }

    @Override
    public long maxDelay() {
        return 3000;
    }
}
