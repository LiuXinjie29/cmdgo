package go;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class NullChess extends Chess{
    NullChess(int x, int y) {
        this.xAxis = x;
        this.yAxis = y;
        this.color = ChessColorEnum.NULL;
    }
    public static NullChess build(int x, int y) {
        return new NullChess(x, y);
    }
}
