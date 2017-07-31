
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

public class EpochTimeServer extends UnicastRemoteObject implements EpochTime {

    public EpochTimeServer() throws RemoteException {
        super();
    }

    public long getEpoch() {
        return System.currentTimeMillis() / 1000;
    }

    public static void main(String[] args) {
        try {
            Naming.rebind("EpochTime", new EpochTimeServer());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
