package cat.udl.rmi.taskexecutor.client;

/**
 * Created by eloigabal on 06/10/2019.
 */

import cat.udl.rmi.taskexecutor.common.Task;

import java.io.Serializable;
import java.math.BigDecimal;

public class Pi2 implements Task<String>, Serializable {

    private static final long serialVersionUID = 227L;

    public Pi2() {
    }

    /**
     * Calculate pi.
     */
    public String getTaskName() { return "Simple string return";}

    public String execute() {
        return "HELLO PI";
    }

}
