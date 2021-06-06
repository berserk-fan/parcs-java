import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import parcs.*;

public class Bluck {
    public static void main(String[] args) throws Exception {
        task curtask = new task();
        curtask.addJarFile("DFS.jar");
        AMInfo info = new AMInfo(curtask, null);
        List<channel> channels = new ArrayList<>();

        for(byte i = 0; i < 2; i++) {
            NodeInfo nodeInfo = new NodeInfo("hello world", 2, i);
            point p = info.createPoint();
            channel c = p.createChannel();
            p.execute("DFS");
            c.write(nodeInfo);
            channels.add(c);
        }

        System.out.println("Waiting for result...");
        String res = null;
        while (res == null) {
            for(channel c : channels) {
                String object = (String)c.readObject();
                System.out.println("SENDING INFO");
                if(!object.equals("STILL IN PROGRESS")) {
                    res = object;
                    break;
                }
            }
        }

        System.out.println("Result: " + Arrays.toString(res.getBytes()));
        curtask.end();
    }
}
