import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @since: 2023/4/7.
 * @Author: LiuXinjie
 */
public class GoClient {

    private final Socket mSocket;

    public GoClient(String host, int port) throws IOException {
        // 创建 socket 并连接服务器
        mSocket = new Socket(host, port);
    }

    public void run() throws IOException {
        // 和服务端进行通信
        Thread readerThread = new Thread(this::readResponse);
        readerThread.start();

        OutputStream out = mSocket.getOutputStream();
        byte[] buffer = new byte[1024];

        //todo 找到一个优雅的方式刷新buffer
        while (System.in.read(buffer) > 0) {
            out.write(buffer);
            System.out.println("send:" + new String(buffer));
            buffer = new byte[1024];
        }
    }

    private void readResponse() {
        try {
            InputStream in = mSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int n;
            while ((n = in.read(buffer)) > 0) {
                System.out.println(IOUtils.toString(buffer));
                buffer = new byte[1024];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] argv) {
        try {
            // 由于服务端运行在同一主机，这里我们使用 localhost
            GoClient client = new GoClient("localhost", 32230);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
