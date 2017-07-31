package Question1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.Date;

public class BankServer {

    public final static int BUFFER_SIZE = 512;
    private byte[] buffer = new byte[BUFFER_SIZE];

    public BankServer(int port) {
        try {
            DatagramSocket socket = new DatagramSocket(port);
            System.out.println("Server started at " + new Date() + "\n");

            DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length);
            DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);

            while (true) {
                Arrays.fill(buffer, (byte) 0);
                String quote;
                socket.receive(requestPacket);
                quote = new String(buffer).trim();
                double annualInterestRate = Double.parseDouble(quote);
                socket.receive(requestPacket);
                quote = new String(buffer).trim();
                double numberOfYears = Double.parseDouble(quote);
                socket.receive(requestPacket);
                quote = new String(buffer).trim();
                double loanAmount = Double.parseDouble(quote);
                Loan loan = new Loan(annualInterestRate, (int) numberOfYears, loanAmount);

                responsePacket.setAddress(requestPacket.getAddress());;
                responsePacket.setPort(requestPacket.getPort());
                responsePacket.setData(Double.toString(loan.getMonthlyPayment()).getBytes());
                socket.send(responsePacket);
                responsePacket.setData(Double.toString(loan.getTotalPayment()).getBytes());
                socket.send(responsePacket);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        int port;
        if (args.length == 0) {
            port = 8000;
            new BankServer(port);
        } else if (args.length == 1) {
            port = Integer.parseInt(args[0]);
            new BankServer(port);
        } else {
            System.out.println("Invalid number of arguments!");
        }
    }

}
