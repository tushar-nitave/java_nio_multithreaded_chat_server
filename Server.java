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



    }

}