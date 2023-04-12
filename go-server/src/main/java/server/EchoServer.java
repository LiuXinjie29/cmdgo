package server;

import go.ChessColorEnum;
import go.Chessboard;
import go.Player;
import org.apache.commons.io.IOUtils;
import server.command.DropCommandRunner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * @since: 2023/4/7.
 * @Author: LiuXinjie
 */
public class EchoServer {

    private final ServerSocket mServerSocket;

    private static Chessboard chessboard = Chessboard.buildQuadrate(5);

    public EchoServer(int port) throws IOException {
        // 1. 创建一个 ServerSocket 并监听端口 port
        mServerSocket = new ServerSocket(port);
    }

    public void run() throws IOException {
        // 2. 开始接受客户连接
        Socket client = mServerSocket.accept();
        handleClient(client);
    }

    private void handleClient(Socket socket) throws IOException {
        // 3. 使用 socket 进行通信 ...
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        DropCommandRunner dropCommandRunner = new DropCommandRunner(Player.join("glb", chessboard, ChessColorEnum.BLACK));

        byte[] buffer = new byte[1024];
        int n;
        while ((n = in.read(buffer)) > 0) {
            String command = new String(buffer).trim();
            System.out.println(command);
            String result = dropCommandRunner.execute(command);
            System.out.println(result);
            System.out.println(Arrays.toString(result.getBytes()));
            out.write(result.getBytes(), 0, n);
        }


    }


    public static void main(String[] argv) {
        try {
            EchoServer server = new EchoServer(9877);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
