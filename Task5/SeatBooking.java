
public class SeatBooking {
    private final boolean[] booked;
    private int bookingsMade = 0;

    SeatBooking(int seats) {
        booked = new boolean[seats];
    }

    // Without synchronization
    public void bookUnsynchronized(int seat) {
        if (!booked[seat]) {
            Thread.yield();
            booked[seat] = true;
            bookingsMade++;
        }
    }

    // Synchronized method
    public synchronized void bookMethod(int seat) {
        if (!booked[seat]) {
            booked[seat] = true;
            bookingsMade++;
        }
    }

    // Synchronized block
    public void bookBlock(int seat) {
        synchronized (this) {
            if (!booked[seat]) {
                booked[seat] = true;
                bookingsMade++;
            }
        }
    }

    public int getBookingsMade() {
        return bookingsMade;
    }

    static int test(int type) throws InterruptedException {
        SeatBooking screen = new SeatBooking(10);
        Thread[] users = new Thread[100];

        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;

            users[i] = new Thread(() -> {
                if (type == 1) {
                    screen.bookUnsynchronized(seat);
                } else if (type == 2) {
                    screen.bookMethod(seat);
                } else {
                    screen.bookBlock(seat);
                }
            });

            users[i].start();
        }

        for (Thread t : users) {
            t.join();
        }

        return screen.getBookingsMade();
    }

    public static void main(String[] args)
            throws InterruptedException {

        System.out.println("Without synchronization: "
            + test(1));

        System.out.println("Synchronized method: "
            + test(2));

        System.out.println("Synchronized block: "
            + test(3));
    }
}