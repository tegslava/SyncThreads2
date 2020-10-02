import sync_threads.Chef;
import sync_threads.Visitors;
import sync_threads.Waiter;
import sync_threads.Worker;

public class Main {
    public static void main(String[] args) {
        Worker worker = new Worker();
        Waiter waiter1 = new Waiter(worker, "Официант1");
        Waiter waiter2 = new Waiter(worker, "Официант2");
        Waiter waiter3 = new Waiter(worker, "Официант3");
        Chef chef = new Chef(worker, "Повар");
        Visitors visitors = new Visitors(worker, "Посетители");
        ThreadGroup mainGroup = new ThreadGroup("Ресторан");
        new Thread(mainGroup, visitors::makeOrderCycle, visitors.getName()).start();
        new Thread(mainGroup, waiter1::takeOrderCycle, waiter1.getName()).start();
        new Thread(mainGroup, waiter2::takeOrderCycle, waiter2.getName()).start();
        new Thread(mainGroup, waiter3::takeOrderCycle, waiter3.getName()).start();
        new Thread(mainGroup, chef::cookOrderCycle, chef.getName()).start();
        new Thread(mainGroup, waiter1::bringOrderCycle, waiter1.getName()).start();
        new Thread(mainGroup, waiter2::bringOrderCycle, waiter2.getName()).start();
        new Thread(mainGroup, waiter3::bringOrderCycle, waiter3.getName()).start();
        new Thread(mainGroup, visitors::eatOrderCycle, visitors.getName()).start();
        new Thread(mainGroup, ()->{worker.getServicedVisitors();}, "").start();
    }
}
