package go;


import org.junit.jupiter.api.Test;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
class ChessboardTest {

    private static final Chessboard chessboard = Chessboard.build(3,4);

    @Test
    void printTest() {
        System.out.println(chessboard.print());
    }

    @Test
    void putTest() {
        chessboard.put("A", "03", ChessColorEnum.WHITE);
        chessboard.put("C", "2", ChessColorEnum.BLACK);
        System.out.println(chessboard.print());
    }

}