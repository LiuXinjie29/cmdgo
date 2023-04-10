package go;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
class PlayerTest {



    @Test
    void putTest() {
        Chessboard chessboard = Chessboard.buildQuadrate(3);
        Player lwb = Player.join("lwb", chessboard, Chess.WHITE);
        Player glb = Player.join("glb", chessboard, Chess.BLACK);
        System.out.println(lwb.drop("A,1"));
        System.out.println(glb.drop("B,1"));
    }

}