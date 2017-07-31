package Question1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class BankClient {
	public final static int BUFFER_SIZE = 512;
	private byte[] buffer = new byte[BUFFER_SIZE];
	private String serverHost;
	private int serverPort;
	private InetAddress serverAddress;
	private DatagramSocket socket;
	private DatagramPacket requestPacket;
	private DatagramPacket responsePacket;
	
	public BankClient(String serverHost, int serverPort){
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		
		try{
			socket = new DatagramSocket();
			
			serverAddress = InetAddress.getByName(this.serverHost);
			
			requestPacket = new DatagramPacket(buffer, buffer.length, this.serverAddress, this.serverPort);
			responsePacket = new DatagramPacket(buffer, buffer.length);
			
			displayConsole();
		}catch (IOException ex){
			System.err.println(ex);
		}
	}
	
	private void displayConsole(){
		double annualInterestRate;
		int numberOfYears;
		double loanAmount;
		
		Scanner in = new Scanner(System.in);
		
		annualInterestRate = in.nextDouble();
		numberOfYears = in.nextInt();
		loanAmount = in.nextDouble();
		try{
			requestPacket.setData(Double.toString(annualInterestRate).getBytes());
			socket.send(requestPacket);
			requestPacket.setData(Integer.toString(numberOfYears).getBytes());
			socket.send(requestPacket);
			requestPacket.setData(Double.toString(loanAmount).getBytes());
			socket.send(requestPacket);
			
			socket.receive(responsePacket);
			String monthlyPayment = new String(buffer).trim();
			socket.receive(responsePacket);
			String totalPayment = new String(buffer).trim();
			
			System.out.println("Monthly Payment = " + monthlyPayment);
			System.out.println("Total Payment = " + totalPayment);
		} catch (IOException ex){
			System.err.println(ex);
		}
	}
	
	
	
	public static void main(String args[]){
		if(args.length == 2){
			//args[0] => hostname
			//args[1] => port
			//new BankClient(args[0], Integer.parseInt(args[1]));
			
		} else {
			new BankClient("localhost", 8000);
			//System.out.println("Invalid number of arguments!");
		}
	}
}


