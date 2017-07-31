
import java.rmi.*;

public interface EpochTime extends Remote {

    public long getEpoch() throws RemoteException;
}
