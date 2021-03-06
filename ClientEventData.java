import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ClientEventData {
		String Cname;
		Point Clocation;
		Direction Cdirection;
		int Ctype;			//0 is remote client, 1 is robot
		static ConcurrentHashMap<String, Client> clientMap = new ConcurrentHashMap<String, Client>();
		private static ClientEventData instance = null;
		
		public static ClientEventData getInstance() {
			if(instance == null) {
		     	instance = new ClientEventData();
		 	 }
		 	return instance;
			/*this.Cname = name;
			this.Clocation = position;
			this.Cdirection = direction;
			this.Ctype = type;*/
		}
	
		public void set_client(Client client){
			this.clientMap.put(Cname,client);
		}
		
}
