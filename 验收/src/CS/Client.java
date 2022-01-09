package CS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    // 用户Socket对象
    private static Socket client;
    // 网络字符输入输出流IO
    private static ObjectInputStream input;
    private static ObjectOutputStream output;
    // 从服务端接收的信息
    private static String messageFromServer;
    // 连接服务器
    public static void ConnectToServer() throws UnknownHostException, IOException {
        System.out.println("\n正在连接服务器，请等候...\n");
        // Socket构造函数参数为IP地址与端口号
        client = new Socket("127.0.0.1", 8888);
        System.out.println("已连接至:" + client.getInetAddress().getHostName());
    }

    // 构造IO流
    public static void GetStreams() throws IOException {
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush();
        input = new ObjectInputStream(client.getInputStream());
        System.out.println("IO流已经构造完成\n");
    }

    // 断开连接
    public static void CloseConnection() throws IOException {
        output.close();
        input.close();
        client.close();
    }

    // 用户向服务器发送消息
    public static void SendMessage(String message) throws IOException {
        // 写入输出流
        output.writeObject(message);
        output.flush();
    }

    // 接收服务器回传的消息
    public static void ReceiveMessage() throws ClassNotFoundException, IOException {
        // 读入输入流
        messageFromServer = (String) input.readObject();
        System.out.println("SERVER>>> " + messageFromServer);
    }


}
