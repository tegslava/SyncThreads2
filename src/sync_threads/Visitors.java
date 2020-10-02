package sync_threads;

public class Visitors {
    private final int VISITOR_INTERVAL = 2000;
    private final int MAX_VISITORS_CNT = 5;
    private final Worker worker;
    private final String name;
    private int servicedVisitorsCnt = 0;

    public Visitors(Worker worker, String name) {
        this.worker = worker;
        this.name = name;
    }

    public void makeOrderCycle() {
        for (int i = 0; i < MAX_VISITORS_CNT; i++) {
        //while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(VISITOR_INTERVAL);
                worker.makeOrder();
            } catch (InterruptedException e) {
                //System.out.println("catch (InterruptedException e) makeOrderCycle()");
                //System.out.println("Извините, мы закрыты");
                //Thread.currentThread().interrupt();
                //e.printStackTrace();
            }
        }
    }

    public void eatOrderCycle() {
        while(!Thread.currentThread().isInterrupted())
            worker.eatOrder();
        }

    public String getName() {
        return name;
    }
}
