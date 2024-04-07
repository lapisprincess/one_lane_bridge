/**
 * One laned implementation of Bridge
 * @author Tilda~
 */
public class OneLaneBridge extends Bridge {

    private int limit;
    private Object directionCheck = new Object();
    private Object sizeCheck = new Object();
    
    public OneLaneBridge(int limit) {
        this.limit = limit;
    }

    public void arrive(Car car) throws InterruptedException {
        synchronized (directionCheck) {
            while (car.getDirection() != direction) {
                directionCheck.wait();
            }
        }
        synchronized (sizeCheck) {
            while (bridge.size() >= limit) {
                sizeCheck.wait();
            }
            car.setEntryTime(currentTime++);
            bridge.add(car);
            System.out.println(this.toString());
        }
    }

    public synchronized void exit(Car car) throws InterruptedException {
        while (bridge.indexOf(car) != 0) {
            wait();
        }
        synchronized (sizeCheck) {
            bridge.remove(car);
            System.out.println(this.toString());
            notifyAll();
            sizeCheck.notifyAll();
        }
        synchronized (directionCheck) {
            if (bridge.size() == 0) {
                direction = !direction;
                directionCheck.notifyAll();
            }
        }
    }

    public String toString() {
        String out = "Bridge (dir="+direction+"): [";
        for (int i = 0; i < bridge.size(); i++) {
            out += bridge.get(i).toString();
            if (i < bridge.size() - 1) out += ", ";
        }
        return out + ']';
    }

}
