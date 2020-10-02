package sync_threads;

public class Waiter {
    private final Worker worker;
    private final String name;

    public Waiter(Worker worker, String name) {
        this.worker = worker;
        this.name = name;
        System.out.printf("%s: на работе!\n", name);
    }

    public String getName() {
        return name;
    }

    public void takeOrderCycle() {
        while (!Thread.currentThread().isInterrupted()) {
            worker.takeOrder();
        }
    }

    public void bringOrderCycle() {
        while (!Thread.currentThread().isInterrupted()) {
            {
                worker.bringOrder();
            }
        }
    }
}
