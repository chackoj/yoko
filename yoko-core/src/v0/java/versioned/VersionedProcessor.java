package versioned;

import java.rmi.RemoteException;

public interface VersionedProcessor {
    String getVersion() throws RemoteException;
}
