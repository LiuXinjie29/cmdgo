package server.command;

import go.Chessboard;
import go.Player;

/**
 * @since: 2023/4/12.
 * @Author: LiuXinjie
 */
public class CreateCommandExecutor extends AbstractCommandExecutor {

    protected CreateCommandExecutor(Player player) {
        super(player, CREATE);
    }

    @Override
    protected String doExecute(String commandContent) {
        Integer boardSize;
        try {
            boardSize = Integer.valueOf(commandContent);
        } catch (Exception e) {
            return "wrong board size";
        }
        Chessboard chessboard = Chessboard.buildQuadrate(boardSize);
        String result = "chessboardNo:" + chessboard.getChessboardNumber() + "\n" + this.player.join(chessboard.getChessboardNumber());
        return result;
    }

}
