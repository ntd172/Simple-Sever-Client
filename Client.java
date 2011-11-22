import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		String host = args[0];
		int port = Integer.parseInt(args[1]);

		Socket socket = new Socket(InetAddress.getByName(host), port);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		DataInputStream in = new DataInputStream(socket.getInputStream());

		SimpleClientThread test1 = new SimpleClientThread(out, in, false); 
		SimpleClientThread test2 = new SimpleClientThread(out, in, true); 
		
		test1.start(); 
		test2.start();
	}

	static class SimpleClientThread extends Thread {
		private DataOutputStream out;
		private DataInputStream in;
		private boolean write = false;

		public SimpleClientThread(DataOutputStream out, DataInputStream in,
				boolean write) {
			this.out = out;
			this.in = in;
			this.write = write;
		}

		public void run() {
			try {
				if (write) {
					Scanner scan = new Scanner(System.in);
					while (true) {
						System.out.print("[SEND] :");
						out.writeUTF(scan.nextLine());
					}
				} else {
					while (true) {
						if (in.available() != 0)
							System.out.println("[RECEIVED] :" + in.readUTF());
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
