package server;

import go.Player;
import server.command.CommandRunner;

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

    public void start() throws IOException {
        System.out.println("server start");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection from " + clientSocket);
            Player curPlayer = Player.build(String.valueOf(clientSocket.getPort()));
            //当前socket 即为一个用户
            //等待用户发送信息，进行对应的操作
            //响应回需要的信息
            // 创建一个新的线程来处理与该客户端的通信
            Thread thread = new Thread(() -> {
                try {
                    // 处理客户端连接
                    handleClient(clientSocket, curPlayer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
    }


    private void handleClient(Socket socket, Player player) throws IOException {
        // 3. 使用 socket 进行通信 ...
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();


        byte[] buffer = new byte[1024];
        //todo 待优化
        while (in.read(buffer) > 0) {
            String command = new String(buffer).trim();
            /*
            todo 应该改成 订阅模式，棋盘有内容输出后马上返回
            Q1: 创建和加入时是不用订阅的，如何只保证在加入棋盘后订阅
             */
            String result = CommandRunner.runCommand(player, command);
            out.write(result.getBytes());
        }
    }

}
