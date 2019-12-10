import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Scanner;


public class Client{
    public static void  main(String[] args) throws IOException, InterruptedException{

        Scanner input = new Scanner(System.in);
        String user_message = "";
        InetSocketAddress clientAddr = new InetSocketAddress("0.0.0.0", 1234);
        SocketChannel client = SocketChannel.open(clientAddr);

        log("Connecting to server");

        ArrayList<String> data = new ArrayList<String>();

        while (true){
            user_message = input.nextLine();
            byte[] message = new String(user_message).getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            client.write(buffer);
            if(user_message.equals("bye"))
                break;
            user_message = "";
            System.out.println("\n");
            buffer.clear();
        }

        client.close();
    }

    private static void log(String str) { System.out.println(str);}
}