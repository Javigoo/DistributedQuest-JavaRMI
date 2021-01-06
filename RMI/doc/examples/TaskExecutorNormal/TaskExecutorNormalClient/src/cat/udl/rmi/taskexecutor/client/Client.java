package cat.udl.rmi.taskexecutor.client;

import cat.udl.rmi.taskexecutor.common.Compute;
import cat.udl.rmi.taskexecutor.common.Task;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by eloigabal on 06/10/2019.
 */

public class Client {
    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry();
            Compute comp = (Compute) registry.lookup(name);
            Pi task = new Pi(5);
            BigDecimal pi = comp.executeTask(task) ;
            System.out.println(pi);
            Pi2 task2 = new Pi2();
            String pi2 = comp.executeTask(task2) ;
            System.out.println(pi2);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }
}