package go;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class Chess {

    private int xAxis;

    private int yAxis;

    ChessColorEnum color;

    public static Chess build(int x, int y, ChessColorEnum color) {
        Chess chess = new Chess();
        chess.xAxis = x;
        chess.yAxis = y;
        chess.color = color;
        return chess;
    }

    String getChessColor() {
        return this.color.getCharacter();
    }
}
