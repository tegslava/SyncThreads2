package sync_threads;

public class Chef {
    private final Worker worker;
    private final String name;

    public Chef(Worker worker, String name) {
        this.worker = worker;
        this.name = name;
        System.out.printf("%s: на работе!\n", name);
    }

    public String getName() {
        return name;
    }

    public void cookOrderCycle() {
        while (!Thread.currentThread().isInterrupted()) {
            worker.cookOrder();
        }
    }
}
