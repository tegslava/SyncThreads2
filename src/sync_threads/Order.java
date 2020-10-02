package sync_threads;

public class Order {
    private final String visiter;
    //private String waiter;
    private final String visiter_orig_name;
    public Order(String visiter, String waiter, String visiter_orig_name) {
        this.visiter = visiter;
        //this.waiter = waiter;
        this.visiter_orig_name = visiter_orig_name;
    }

    public String getVisiter() {
        return visiter;
    }

    /*public String getWaiter() {
        return waiter;
    }*/

    /*public void setWaiter(String waiter) {
        this.waiter = waiter;
    }*/

    public String getVisiter_orig_name() {
        return visiter_orig_name;
    }
}
