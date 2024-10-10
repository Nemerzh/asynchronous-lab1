public class People implements Runnable {

    @Override
    public void run() {
        try {
            System.err.printf("Гість з потоку %s прийшов до кав'ярні \n", Thread.currentThread().getName());
            Thread.sleep(20);

            Cafe.barista.acquire();
            System.out.printf("Гість з потоку %s робить замовлення \n", Thread.currentThread().getName());
            Thread.sleep(2000);
            Cafe.barista.release();

            Cafe.order.acquire();
            System.out.printf("Бариста готує замовлення для гостя з потоку %s \n", Thread.currentThread().getName());
            Thread.sleep(4000);

            Cafe.order.release();
            System.out.printf("Бариста віддає замовлення гостю з потоку %s \n", Thread.currentThread().getName());
            Thread.sleep(20);

            Thread.sleep(4000);

            System.out.printf("Гість з потоку %s поїв і пішов \n", Thread.currentThread().getName());
            Thread.sleep(20);


        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
