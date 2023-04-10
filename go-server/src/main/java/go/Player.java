package go;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class Player {

    private String name;

    private Chessboard chessboard;

    private Chess chess;

    static Player join(String name, Chessboard chessboard, Chess chess) {
        Player player = new Player();
        player.name = name;
        player.chessboard = chessboard;
        player.chess = chess;
        return player;
    }

    String drop(String assemblyXY) {
        return this.chessboard.put(assemblyXY, this.chess);
    }

    String drop(String x, String y) {
        return this.chessboard.put(x, y, this.chess);
    }

}
