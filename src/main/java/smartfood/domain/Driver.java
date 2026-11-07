

package smartfood.domain;

public class Driver {

    private int id;
    private String name;
    private DriverStatus status;
    private double distanceToRestaurant;
    private double speed;

    public Driver(int id, String name) {
        this(id, name, 0.0, 0.0);
    }

    public Driver(
            int id,
            String name,
            double distanceToRestaurant,
            double speed) {

        if (distanceToRestaurant < 0) {
            throw new IllegalArgumentException(
                    "Distance cannot be negative");
        }

        if (speed < 0) {
            throw new IllegalArgumentException(
                    "Speed cannot be negative");
        }

        this.id = id;
        this.name = name;
        this.status = DriverStatus.AVAILABLE;
        this.distanceToRestaurant = distanceToRestaurant;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public double getDistanceToRestaurant() {
        return distanceToRestaurant;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isAvailable() {
        return status == DriverStatus.AVAILABLE;
    }

    public void markBusy() {
        if (!isAvailable()) {
            throw new IllegalStateException("Driver is not available");
        }

        status = DriverStatus.BUSY;
    }

    public void markAvailable() {
        status = DriverStatus.AVAILABLE;
    }
}