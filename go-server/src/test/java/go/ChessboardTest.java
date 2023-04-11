package go;


import org.junit.jupiter.api.Test;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
class ChessboardTest {

    private static final Chessboard chessboard = Chessboard.buildQuadrate(5);

    @Test
    void printTest() {
        System.out.println(chessboard.print());
    }

    @Test
    void aroundPutTest() {
        System.out.println(chessboard.put("C", "01", ChessColorEnum.WHITE));
        System.out.println();
        System.out.println(chessboard.put("C", "03", ChessColorEnum.WHITE));
        System.out.println();
        System.out.println(chessboard.put("B", "02", ChessColorEnum.WHITE));
        System.out.println();
        System.out.println(chessboard.put("C", "02", ChessColorEnum.BLACK));
        System.out.println();
        System.out.println(chessboard.put("D", "02", ChessColorEnum.WHITE));
    }

    @Test
    void anglePutTest() {
        System.out.println(chessboard.put("A", "01", ChessColorEnum.WHITE));
        System.out.println(chessboard.put("A", "00", ChessColorEnum.BLACK));
        System.out.println(chessboard.put("B", "00", ChessColorEnum.WHITE));

    }

}