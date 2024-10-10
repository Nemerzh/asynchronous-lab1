import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Cafe {

    // Кількість замовлень, що готуються одночасно
    static final Semaphore order = new Semaphore(2);

    // Кількість замовлень, що приймаються одночасно
    static final Semaphore barista = new Semaphore(1);

    // Чи робочі години
    private static boolean isAvailableHours = true;

    // Список потоків гостей
    private static final List<Thread> guestThreads = new ArrayList<>();

    // Перевірка, чи кав'ярня ще відкрита
    public static synchronized boolean isOpen() {
        return isAvailableHours;
    }

    // Закриття кафе
    public static synchronized void closeCafe() {
        isAvailableHours = false;
        System.err.println("=============Кав'ярню закрили================");
    }

    public static void main(String[] args) throws InterruptedException {

        Runnable cafe = () -> {
            int i = 0;
            System.err.println("=============Кав'ярня відкрита=============");
            while (isOpen()) {
                Thread guestThread = new Thread(new People(), String.valueOf(i));
                guestThreads.add(guestThread);
                guestThread.start();
                i++;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread cafeThread = new Thread(cafe, "Кав'ярня");
        cafeThread.start();  // Відкриття кав'ярні
        Thread.sleep(6000);
        closeCafe();  // Зачинення кав'ярні

        for (Thread guest : guestThreads) {
            guest.join();
        }

        System.err.println("=============Персонал пішов додому================");
    }
}