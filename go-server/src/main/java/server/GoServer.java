package server;

import go.ChessColorEnum;
import go.Chessboard;
import go.Player;
import server.command.DropCommandRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @since: 2023/4/12.
 * @Author: LiuXinjie
 */
public class GoServer {

    private static ServerSocket serverSocket;

    static {
        try {
            serverSocket = new ServerSocket(32230);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Chessboard chessboard = Chessboard.buildQuadrate(5);

    static {
        try {
            serverSocket = new ServerSocket(32230);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        System.out.println("server start");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket);

            // 创建一个新的线程来处理与该客户端的通信
            Thread thread = new Thread(() -> {
                try {
                    // 处理客户端连接
                    handleClient(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }


    private void handleClient(Socket socket) throws IOException {
        // 3. 使用 socket 进行通信 ...
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        DropCommandRunner dropCommandRunner = new DropCommandRunner(Player.join("glb", chessboard, ChessColorEnum.BLACK));

        byte[] buffer = new byte[1024];
        while (in.read(buffer) > 0) {
            String command = new String(buffer).trim();
            System.out.println(command);
            String result = dropCommandRunner.execute(command);
            out.write(result.getBytes());
        }
    }

}
