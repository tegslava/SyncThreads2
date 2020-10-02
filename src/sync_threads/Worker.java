package sync_threads;

import java.util.ArrayList;
import java.util.List;

public class Worker {
    private static final long MAKE_ORDER_TIME = 1000;
    private static final long COOK_ORDER_TIME = 3000;
    private static final long EAT_TIME = 4000;
    private static final long BRING_ORDER_TIME = 2000;
    private final int MAX_VISITORS_CNT = 5;

    private List<Order> orderList = new ArrayList<>();      //принятые заказы
    private List<Order> cookOrderList = new ArrayList<>();  //заказы для повара
    private List<Order> bringOrderList = new ArrayList<>(); //заказы для доставки
    private List<Order> eatOrderList = new ArrayList<>();   //готоые заказы на столе
    private int visitorsCnt = 0;
    private int servicedVisitors = 0;

    public synchronized void makeOrder() {
        //сделать заказ
        try {
            System.out.printf("Посетитель%d в ресторане\n", ++visitorsCnt);
            Thread.sleep(MAKE_ORDER_TIME);
            orderList.add(new Order(String.format("Посетителя%d", visitorsCnt), null, String.format("Посетитель%d", visitorsCnt)));
            System.out.printf("Посетитель%d сделал заказ\n", visitorsCnt);
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void takeOrder() {
        // принять заказ, закинуть в список для повара
        try {
            /*            while ((orderList.size() == 0) *//*||
                    ((bringOrderList.size() > 0) && Thread.currentThread().getName().equals(bringOrderList.get(0).getWaiter()))*//*) {
                wait();
            }*/
            if ((orderList.isEmpty())) {
                wait();
                return;
            }
            System.out.printf("%s: взял заказ %s\n", Thread.currentThread().getName(), orderList.get(0).getVisiter());
            //orderList.get(0).setWaiter(Thread.currentThread().getName());
            cookOrderList.add(orderList.remove(0));
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void cookOrder() {
        // приготовить блюдо, закинуть в список готовых заказоа
        try {
/*            while (cookOrderList.size() == 0) {
                wait();
            }*/
            if ((cookOrderList.isEmpty())) {
                wait();
                return;
            }
            System.out.printf("Повар готовит блюдо %s\n", cookOrderList.get(0).getVisiter());
            Thread.sleep(COOK_ORDER_TIME);
            System.out.printf("Повар приготовил блюдо %s\n", cookOrderList.get(0).getVisiter());
            bringOrderList.add(cookOrderList.remove(0));
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized void eatOrder() {
        // посетитель приступил к еде
        try {
            /*while (eatOrderList.size() == 0) {
                wait();
            }*/
            if ((eatOrderList.isEmpty())) {
                wait();
                return;
            }

            System.out.printf("%s: приступил к еде\n", eatOrderList.get(0).getVisiter_orig_name());
            Thread.sleep(EAT_TIME);
            System.out.printf("%s: вышел из ресторана\n", eatOrderList.get(0).getVisiter_orig_name());
            eatOrderList.remove(0);
            notifyAll();
            servicedVisitors++;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public synchronized void bringOrder() {
        // официант несет готовый заказ
        try {
/*
            while (bringOrderList.size() == 0) {
                wait();
            }
*/
            if ((bringOrderList.isEmpty())) {
                wait();
                return;
            }

            //bringOrderList.get(0).setWaiter("");
            System.out.printf("%s: несет заказ %s\n", Thread.currentThread().getName(), bringOrderList.get(0).getVisiter());
            Thread.sleep(BRING_ORDER_TIME);

            eatOrderList.add(bringOrderList.remove(0));

            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

        public synchronized int getServicedVisitors() {
            try {
                while (servicedVisitors < MAX_VISITORS_CNT) {
                    wait();
                }
                System.out.printf("servecedVisitors %s\n", servicedVisitors);
                Thread.currentThread().getThreadGroup().interrupt();
                //notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return servicedVisitors;
        }
/*    public synchronized void visitorsCounter() {
        while (!Thread.currentThread().isInterrupted()) {
            if (servicedVisitors < MAX_VISITORS_CNT) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().getThreadGroup().interrupt();
                }
            }
        }
        System.out.printf("Обслужили %s посетителей\n", MAX_VISITORS_CNT);
        Thread.currentThread().getThreadGroup().interrupt();
    }*/
}

