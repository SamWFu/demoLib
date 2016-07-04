package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author 付施威
 * @version V1.0
 * @SystemName UTB-CLOUD
 * @ModuleName nio
 * @Date 16/7/2上午11:17
 * @Description 描述 Nio Socket的服务器端代码
 */
public class NioServer {

	public static void main(String[] args) throws IOException {

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(8080));
		serverSocketChannel.configureBlocking(false);// 非阻塞模式
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		Handler handler = new Handler(1024);
		while (true) {

			if (selector.select(3000) == 0) {
				System.out.println("请求超时");
				continue;
			}

			System.out.println("处理请求");

			Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

			while (keyIterator.hasNext()) {

				SelectionKey key = keyIterator.next();
                try {
                    if (key.isAcceptable())
                        handler.handleAccept(key);
                    if (key.isReadable())
                        handler.handleRead(key);
                }catch (IOException e){

                    keyIterator.remove();
                    continue;
                }

                keyIterator.remove();
			}
		}
	}

	private static class Handler {

		private int bufferSize = 1024;
		private String localCharset = "UTF-8";

		public Handler() {
		}

		public Handler(String localCharset) {

			this(-1, localCharset);
		}

		public Handler(int bufferSize) {

			this(bufferSize, null);
		}

		public Handler(int bufferSize, String localCharset) {

			if (bufferSize > 0)
				this.bufferSize = bufferSize;

			if (localCharset != null)
				this.localCharset = localCharset;
		}

		public void handleAccept(SelectionKey key) throws IOException {

			SocketChannel socketChannel = (SocketChannel) key.channel();
			socketChannel.configureBlocking(false);
			socketChannel.register(key.selector(), SelectionKey.OP_READ,
					ByteBuffer.allocate(bufferSize));
		}

		public void handleRead(SelectionKey key) throws IOException {

			SocketChannel socketChannel = (SocketChannel) key.channel();

			ByteBuffer buffer = (ByteBuffer) key.attachment();
			buffer.clear();

			if (socketChannel.read(buffer) == -1) {
				socketChannel.close();
			} else {
				buffer.flip();
				String receivedString = Charset.forName(localCharset).newDecoder().decode(buffer)
						.toString();
				System.out.println("接收到消息" + receivedString);

				String sendString = "received data: " + receivedString;
				buffer = ByteBuffer.wrap(sendString.getBytes(localCharset));
				socketChannel.write(buffer);
				socketChannel.close();
			}
		}
	}
}
