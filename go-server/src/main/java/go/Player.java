package go;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class Player {

    private String name;

    //todo 移出， 创建房间实体类，到时候通过房间来选择棋盘
    private Chessboard chessboard;

    private ChessColorEnum chessColorEnum;

    static Player join(String name, Chessboard chessboard, ChessColorEnum chessColorEnum) {
        Player player = new Player();
        player.name = name;
        player.chessboard = chessboard;
        player.chessColorEnum = chessColorEnum;
        return player;
    }

    String drop(String assemblyXY) {
        return this.chessboard.put(assemblyXY, this.chessColorEnum);
    }

    String drop(String x, String y) {
        return this.chessboard.put(x, y, this.chessColorEnum);
    }

}
