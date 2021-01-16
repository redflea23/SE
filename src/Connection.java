import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
	
	Socket socket;		
	PrintWriter out;
	BufferedReader in;
	int port;
	String ip;
	public Connection(int portNumber, String ip) {
		this.socket = null;
		this.out = null;
		this.in = null;
		this.port = portNumber;
		this.ip = ip;		
	}
	
	public void connectSocket() throws UnknownHostException, IOException {
		socket = new Socket(ip, port);
	}
	
	public void connectIN() throws IOException {
		
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void connectOUT() throws IOException {
		
		out = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void close() throws IOException {
		if(out != null) out.close();
		if(in != null) in.close();
		if(socket != null) socket.close();
	}
	
	public String transfer(String msg, ConnectionBehaviour b) {
		return b.behaviour(msg);
	}
	
	public boolean isClosed() {
		return !socket.isConnected();
	}
}
