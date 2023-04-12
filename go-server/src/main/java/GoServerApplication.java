import server.GoServer;

import java.io.IOException;

/**
 * @since: 2023/4/10.
 * @Author: LiuXinjie
 */
public class GoServerApplication {

    public static void main(String[] args) throws IOException {
        new GoServer().start();
    }
}
