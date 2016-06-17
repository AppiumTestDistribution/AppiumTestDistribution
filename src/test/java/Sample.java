import java.util.ArrayList;
import java.util.List;

public class Sample {
    static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub

        for (int i = 0; i < 4; i++) {
            Thread t = new Thread(() -> {
                System.out.println(
                    Integer.valueOf(Thread.currentThread().getName().split("-")[1]) - 1);

            });

            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Finally complete");
    }

}
