import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MazeServerHandler extends Thread{
	private Socket socket = null;
	static ConcurrentHashMap<String, Client> clientMap = new ConcurrentHashMap<String, Client>();
	static ClientEventData CData = new ClientEventData();
	Client self;
	MazePacket packetFromClient;
	MazePacket packetToClient;
	
	public MazeServerHandler(Socket socket){
		super("MazeServerHandlerThread");
		this.socket = socket;
		System.out.println("Created new Thread to handle client");
	}


	public void run() {
		boolean gotByePacket = false;
		try{
				ObjectInputStream fromClient = new ObjectInputStream(socket.getInputStream());
				
			
				/* stream to write back to client */
				ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
				while (( packetFromClient = (MazePacket) fromClient.readObject()) != null) {
					  switch (packetFromClient.type) {
		                case MazePacket.CLIENT_REGISTER:
		                    Client_Register();
		                    break;
		                case MazePacket.CLIENT_QUIT:
		                    Client_Quit();
		                    break;
		                case MazePacket.CLIENT_FORWARD:
		                    Client_Forward();
		                    break;  
		                case MazePacket.CLIENT_LEFT:
		                    Client_Left();
		                    break;  
		                    
		                case MazePacket.CLIENT_RIGHT:
		                    Client_Right();
		                    break;  
		                case MazePacket.CLIENT_BACKWARD:
		                    Client_Backward();
		                    break;  
		              }
			
				}
		
		
		
				fromClient.close();
				toClient.close();
				socket.close();
			
		}catch (IOException e) {
			if(!gotByePacket)
				e.printStackTrace();
		} catch (ClassNotFoundException e) {
			if(!gotByePacket)
				e.printStackTrace();
		}
	}
	
	
	public void Client_Register(){
		this.packetToClient = new MazePacket();
		System.out.println("client registering for the first time"+packetToClient.Cname);
		if(clientMap.get(this.packetFromClient.Cname)==null){
			System.out.println("client does not exist");
			//clientMap.put(this.packetFromClient.Cname, this.packetFromClient.NC.get_client());
			Broad_cast();
		}
		else{
			Error_sending(MazePacket.CLIENT_REGISTER_ERROR);
		}
	
	}
	
	public void Client_Quit(){
		System.out.println("client quitting");
	
	}

	public void Client_Forward(){
		System.out.println("client moving forward");
		this.packetToClient = new MazePacket();
		System.out.println("client registering for the first time");
		if(clientMap.get(this.packetFromClient.Cname)==null){
			//clientMap.put(this.packetFromClient.Cname, this.packetFromClient.newclient);
			Broad_cast();
		}
		else{
			Error_sending(MazePacket.CLIENT_REGISTER_ERROR);
		}
	}
	
	public void Client_Left(){
		System.out.println("client turning left");
	
	}
	
	public void Client_Right(){
		System.out.println("client turning right");
	
	}
	
	public void Client_Backward(){
		System.out.println("client turning backward");
	
	}
	
	public void Broad_cast(){
		
	}	
	
	
	public void Error_sending(int err_code){
		try{
			packetToClient.type = err_code;
			/* stream to write back to client */
			ObjectOutputStream toClient = new ObjectOutputStream(socket.getOutputStream());
			/* send reply back to client */
			toClient.writeObject(packetToClient);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
