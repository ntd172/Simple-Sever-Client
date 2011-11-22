import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class Server {
	public static final int MAX = 100; // maximum connections that this server can handle
	public static void main(String[] args) throws UnknownHostException, IOException { 
		String host = args[0];
		int port = Integer.parseInt(args[1]); 
		ServerSocket socket = new ServerSocket(port, MAX, InetAddress.getByName(host)); 
		
		Socket clientA = socket.accept();
		System.out.println("first connection etablished.");
		Socket clientB = socket.accept();
		System.out.println("second connection etablslihed.");
		
		DataOutputStream outA = new DataOutputStream(clientA.getOutputStream());
		DataOutputStream outB = new DataOutputStream(clientB.getOutputStream()); 
		
		DataInputStream inA = new DataInputStream(clientA.getInputStream()); 
		DataInputStream inB = new DataInputStream(clientB.getInputStream());
		
		SimpleThread test1 = new SimpleThread(outA, inB); 
		SimpleThread test2 = new SimpleThread(outB, inA); 
		test1.start(); 
		test2.start(); 
	}
	
	static class SimpleThread extends Thread {
		private DataOutputStream out; 
		private DataInputStream in; 
		
		public SimpleThread(DataOutputStream out, DataInputStream in) {
			this.out = out; 
			this.in = in; 
		}
		
		public void run() {
			while (true) {
				try {
					String data = null; 
					if (in.available() != 0) 
						data = in.readUTF();
					if (data != null) {
						System.out.println(data); 
						out.writeUTF(data); 
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
}
