package Views;

import Models.Location;
import Models.Player;
import ObserveablePattern.Subject;

import java.util.List;

// THIS IS AN EXAMPLE. The methods aren't final.
public interface BoardSubject extends Subject<BoardSubject> {
    List<Location> getLocations();
    List<Player> getPlayers();
}
