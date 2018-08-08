package Utils;

import android.location.Location;

public interface LocationTrackerInterface {
    public interface LocationUpdateListener{
        public void onUpdate(Location oldLocation,long oldTime,Location newLocation,long newTime);
    }
    public void start();
    public void start(LocationUpdateListener update);

    public void stop();

    public boolean hasLocation();

    public boolean hasPossiblyStaleLocation();

    public Location getLocation();

    public Location getPossiblyStaleLocation();
}
