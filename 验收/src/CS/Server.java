package CS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@SuppressWarnings({"all"})
// 将Server继承于Thread类，实现多线程。由于没有Server没有继承其他父类，所有继承Threa类，否则实现Runnable接口
public class Server extends Thread {

    // 网络字节输出流(输出至不同用户)
    private static ArrayList<ObjectOutputStream> outputToClients;
    // 网络字节输入流
    private ObjectInputStream input;

    // 服务器的ServerSocket对象
    private static ServerSocket server;
    // 接收连接用户的Socket对象connection
    private Socket connection;

    // 线程编号，从0开始
    private static int counter = 0;
    // 线程名称
    private String name;

    // 构造方法
    public Server(Socket connection, String name) {
        this.connection = connection;
        this.name = name;
        try {
            // 构建input流
            input = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 运行态方法
    public void run() {
        try {
            // 接收用户的信息
            String messageFromClient;

            // 构建output流
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            outputToClients.add(output);
            System.out.println("IO构造完成\n");

            do {
                // 读取用户信息
                messageFromClient = (String) input.readObject();
                System.out.println(this.name + ":" + messageFromClient);

                // 回传消息
                output.writeObject(messageFromClient);
                output.flush();

            } while (!messageFromClient.equals("登出")); // 只要未登出反复循环监听

            // 回传登出消息
            output.writeObject(messageFromClient);
            output.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            // 创建服务器对象
            server = new ServerSocket(8888);
            outputToClients = new ArrayList<ObjectOutputStream>();

            // 反复循环实现不断接收用户的效果
            while (true) {
                // 反复等待
                Socket connection = server.accept();
                System.out.println("\n等待连接...\n");
                // 编号增加
                counter++;
                System.out.println("Thread " + counter + ":已连接" + connection.getInetAddress().getHostName());
                // 新增线程
                Thread t = new Server(connection, "Thread " + counter);
                // 使线程进入就绪态，一旦有CPU资源，将直接进入run()方法
                t.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
