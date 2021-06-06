import parcs.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class DFS implements AM {
    private volatile String result = null;
    private int processors = Runtime.getRuntime().availableProcessors();
    private ExecutorService pool = Executors.newFixedThreadPool(processors);

    public void run(AMInfo info) {
        if(info == null) {
            throw new IllegalArgumentException("info is null!!");
        }

        NodeInfo n = (NodeInfo)info.parent.readObject();
        System.out.println("[" + n.getS() + "] Hash started.");

        for(byte i = 0; i < processors - 1; i++) {
            System.out.println("Starting thread " + i);
            final byte namespaceInProcess = i;
            pool.submit(() -> {
                byte[] salt = new byte[10];
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    byte[] digest = new byte[n.getBits()];
                    for (int j = 0; j < n.getBits(); ++j) {
                        digest[j] = 1;
                    }
                    while (existsNonZero(digest, n.getBits()) && result == null) {
                        new Random().nextBytes(salt);
                        salt[0] = n.getNamespace();
                        salt[1] = namespaceInProcess;
                        md.update(n.getS().getBytes());
                        digest = md.digest(salt);
                        md.reset();
                    }
                    System.out.println("EXITED: " + namespaceInProcess);
                    DFS.this.result = new String(salt);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            });
        }

        System.out.println("[" + n.getS() + "] Waiting pool...)");
        while(result == null) {
            info.parent.write("STILL IN PROGRESS");
            try {
                Thread.sleep(3000);
                System.out.println("ALIVE");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("EXITED: NOTIFIER: " + result);
        info.parent.write(result);
        System.out.println("[" + n.getS() + "] Hash finished. Salt: " + result);
    }

    private boolean existsNonZero(byte[] digest, int bits) {
        for(int i = 0; i < bits; ++i) {
            if(digest[digest.length - i - 1] != 0) {
                return true;
            }
        }
        return false;
    }
}
