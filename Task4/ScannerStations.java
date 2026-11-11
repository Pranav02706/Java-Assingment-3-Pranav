
class ScannerThread extends Thread {
    int scripts;

    ScannerThread(String name, int scripts) {
        super(name);
        this.scripts = scripts;
    }

    public void run() {
        try {
            System.out.println(getName() + " started");

            Thread.sleep(1000);

            System.out.println(getName() +
                " scanned " + scripts + " scripts");

        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupted");
            Thread.currentThread().interrupt();
        }
    }
}

class ScannerRunnable implements Runnable {
    int scripts;
    String name;

    ScannerRunnable(String name, int scripts) {
        this.name = name;
        this.scripts = scripts;
    }

    public void run() {
        try {
            System.out.println(name + " started");

            Thread.sleep(1000);

            System.out.println(name +
                " scanned " + scripts + " scripts");

        } catch (InterruptedException e) {
            System.out.println(name + " interrupted");
            Thread.currentThread().interrupt();
        }
    }
}

public class ScannerStations {
    public static void main(String[] args)
            throws InterruptedException {

        int total = 0;

        System.out.println("Using Thread class:");

        ScannerThread t1 = new ScannerThread("Station 1", 10);
        ScannerThread t2 = new ScannerThread("Station 2", 20);
        ScannerThread t3 = new ScannerThread("Station 3", 15);
        ScannerThread t4 = new ScannerThread("Station 4", 25);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        System.out.println("Station 1 alive: " + t1.isAlive());

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        total = 10 + 20 + 15 + 25;

        System.out.println("Total scripts scanned: " + total);

        System.out.println("\nUsing Runnable interface:");

        Thread r1 = new Thread(
            new ScannerRunnable("Station 1", 10)
        );
        Thread r2 = new Thread(
            new ScannerRunnable("Station 2", 20)
        );
        Thread r3 = new Thread(
            new ScannerRunnable("Station 3", 15)
        );
        Thread r4 = new Thread(
            new ScannerRunnable("Station 4", 25)
        );

        r1.start();
        r2.start();
        r3.start();
        r4.start();

        r1.join();
        r2.join();
        r3.join();
        r4.join();

        System.out.println("All Runnable stations completed");
    }
}
