package Views;

import Models.Player;
import ObserveablePattern.Subject;

import java.util.List;

public interface LobbySubject extends Subject<LobbySubject> {

    List<Player> getPlayers();
}
