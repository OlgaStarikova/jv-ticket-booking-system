package mate.academy;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketBookingSystem {

    private int totalSeats;
    private Semaphore semaphore;
    private Lock lock = new ReentrantLock();

    public TicketBookingSystem(int totalSeats) {
        this.totalSeats = totalSeats;
        this.semaphore = new Semaphore(totalSeats);
    }

    public BookingResult attemptBooking(String user) {
        BookingResult bookingResult;
        if (semaphore.tryAcquire()) {
            synchronized (lock) {
                lock.lock();
                bookingResult = new BookingResult(user, true, "Booking successful.");
                this.totalSeats--;
                semaphore.release();
                semaphore = new Semaphore(totalSeats);
                lock.unlock();
            }
        } else {
            bookingResult = new BookingResult(user, false, "No seats available.");
        }
        return bookingResult;
    }
}
