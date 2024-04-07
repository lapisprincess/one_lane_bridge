/**
 * Runs all threads
 */
public class BridgeRunner {

    public static void main(String[] args) {
        if (args.length < 2)  {
            System.err.println("Usage: BridgeRunner <bridge limit> <num cars>");
            return;
        }
        int bridgeLimit = Integer.parseInt(args[0]);
        OneLaneBridge bridge = new OneLaneBridge(bridgeLimit);
        int numCars = Integer.parseInt(args[1]);
        Thread[] allCars = new Thread[numCars + 1];
        for (int i = 0; i <= numCars; i++) {
            allCars[i] = new Thread(new Car(i, bridge));
            allCars[i].start();
        }
        for (Thread t : allCars) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("All cars have crossed!!");
    }

}
