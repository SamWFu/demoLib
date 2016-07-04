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
public class HttpServer {

	public static void main(String[] args) throws IOException {

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(8080));
		serverSocketChannel.configureBlocking(false);// 非阻塞模式
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {

			if (selector.select(3000) == 0) {
				continue;
			}

			Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();

			while (keyIterator.hasNext()) {

				SelectionKey key = keyIterator.next();

				new Thread(new HttpHandler(key)).run();
				keyIterator.remove();
			}
		}
	}

	private static class HttpHandler implements Runnable {

		private int bufferSize = 1024;
		private String localCharset = "UTF-8";

		private SelectionKey key;

		public HttpHandler() {
		}

		public HttpHandler(SelectionKey key) {

			this.key = key;
		}

		public void handleAccept() throws IOException {

			SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
			socketChannel.configureBlocking(false);
			socketChannel.register(key.selector(), SelectionKey.OP_READ,
					ByteBuffer.allocate(bufferSize));
		}

		public void handleRead() throws IOException {

			SocketChannel socketChannel = (SocketChannel) key.channel();

			ByteBuffer buffer = (ByteBuffer) key.attachment();
			buffer.clear();

			if (socketChannel.read(buffer) == -1) {
				socketChannel.close();
			} else {
				buffer.flip();
				String receivedString = Charset.forName(localCharset).newDecoder().decode(buffer)
						.toString();
				String[] requestMessage = receivedString.split("\r\n");

				for (String s : requestMessage) {
					System.out.println(s);
					if (s.isEmpty()) {
						break;
					}
				}

				String[] firstLine = requestMessage[0].split(" ");
				System.out.println();
				System.out.println("Method: \t" + firstLine[0]);
				System.out.println("url: \t" + firstLine[1]);
				System.out.println("HTTP Version: \t" + firstLine[2]);
				System.out.println();

				// 返回值
				StringBuilder sendString = new StringBuilder();
				sendString.append("HTTP/1.1 200 OK\r\n");
				sendString.append("Content-Type:text/html;charset=" + localCharset + "\r\n");
				sendString.append("\r\n");

				sendString.append(
						"<html><head><meta charset=\"UTF-8\">\n<title>My Response</title>\n</head>\n<body>\n");
				sendString.append("接收到的请求报文是:</br>");
				for (String s : requestMessage) {

					sendString.append(s + "<br/>");
				}

				sendString.append("</body></html>");

				buffer = ByteBuffer.wrap(sendString.toString().getBytes(localCharset));
				socketChannel.write(buffer);
				socketChannel.close();
			}
		}

		@Override
		public void run() {

			try {
				if (key.isAcceptable()) {

					handleAccept();
				}

				if (key.isReadable()) {
					handleRead();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
