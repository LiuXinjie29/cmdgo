package go;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class NullChess extends Chess{
    NullChess() {
        this.color = ChessColorEnum.NULL;
    }
    public static NullChess build() {
        return new NullChess();
    }
}
