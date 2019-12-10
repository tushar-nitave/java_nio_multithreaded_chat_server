import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.Enumeration;
import java.util.Collections;
import static java.lang.System.out;

public class Server{

    public static void main(String[] args) throws IOException {
        InetAddress ip;
        String hostname;

        /**
         *  Selector is used for handling multiple channels using a single thread.
         *  It is a multiplexor of selectable channels.
         */
        Selector selector = Selector.open();

        /**
         *  Connecting a channel with a TCP network socket.
         *  Binding the channel's socket to all the address
         */
        ServerSocketChannel socket = ServerSocketChannel.open();
        InetSocketAddress ipAddr = new InetSocketAddress("0.0.0.0", 1234);
        socket.bind(ipAddr);
        socket.configureBlocking(false);

        /** Returns set of valid operations */
        int ops = socket.validOps();

        /**
         * Channels should be registered with selctor for monitoring.
         */
        SelectionKey selectKey = socket.register(selector, ops, null);

        /**
         * Infinite loop...
         * Keep server running
         */
        while(true) {

            /** Select set of keys whose corresponding channels are ready*/
            selector.select();

            /** Token representing the registration of a SelectableChannel*/
            Set<SelectionKey> serverKeys = selector.selectedKeys();
            Iterator<SelectionKey> serverIterator = serverKeys.iterator();

            while (serverIterator.hasNext()) {
                SelectionKey serverKey = serverIterator.next();

                /**Test if channel is ready to accept a new socket connection*/
                if (serverKey.isAcceptable()) {
                    SocketChannel client = socket.accept();

                    /**Adjust channels blocking mode to false*/
                    client.configureBlocking(false);

                    /**Operation-set bit for read operations*/
                    client.register(selector, SelectionKey.OP_READ);
                    log("Connected: " + client.getLocalAddress() + "\n");

                } else if (serverKey.isReadable()) {
                    SocketChannel client = (SocketChannel) serverKey.channel();
                    ByteBuffer serverBuffer = ByteBuffer.allocate(256);
                    client.read(serverBuffer);
                    String result = new String(serverBuffer.array()).trim();
                    log("Client: " + result);

                    if (result.equals("bye")) {
//                        client.close();
                        log("\nIt's time to close.");
                        client.close();
                        break;
                    }
                }
                serverIterator.remove();
            }
        }
    }

    private static void log(String str) {System.out.println(str+"\n");}
}