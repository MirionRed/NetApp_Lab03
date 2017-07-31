
import java.io.*;
import java.net.*;
import java.rmi.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EpochTimeClient extends JFrame {
    // LineService

    private static EpochTime epochTime;

    public EpochTimeClient() {
        displayFrame();
    }

    private void displayFrame() {
        try {
            long epochResult = epochTime.getEpoch();
            System.out.println(epochResult);
        } catch (RemoteException ex) {
            System.out.println(ex);
        }

    }

    public static void main(String args[]) {
        if (args.length == 1) {
            try {
                System.setSecurityManager(new RMISecurityManager());
                epochTime = (EpochTime) Naming.lookup("//" + args[0] + "/EpochTime");
                new EpochTimeClient();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            System.out.println("Invalid number of arguments!");
        }
    }
}
