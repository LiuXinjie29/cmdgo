package server.command;

import go.Chessboard;
import go.Player;

/**
 * @since: 2023/4/12.
 * @Author: LiuXinjie
 */
public class JoinCommandExecutor extends AbstractCommandExecutor {

    protected JoinCommandExecutor(Player player) {
        super(player, JOIN);
    }

    @Override
    protected String doExecute(String commandContent) {
        if (!Chessboard.ChessboardMap().containsKey(commandContent)) {
            return "don't exists " + commandContent + " chessboard";
        }
        return this.player.join(commandContent);
    }

}
