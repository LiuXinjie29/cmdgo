package server.command;

import go.Chessboard;
import go.Player;

/**
 * @since: 2023/4/11.
 * @Author: LiuXinjie
 */
public class DropCommandRunner extends AbstractCommandRunner{

    private Player player;

    public DropCommandRunner(Player player) {
        this.player = player;
    }

    @Override
    protected String doExecute(String commandContent) {
        return player.drop(commandContent);
    }

    @Override
    protected String command() {
        return "drop";
    }
}
