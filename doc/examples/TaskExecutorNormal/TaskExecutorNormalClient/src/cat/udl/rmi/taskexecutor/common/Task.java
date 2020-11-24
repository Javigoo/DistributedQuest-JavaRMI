package cat.udl.rmi.taskexecutor.common;

/**
 * Created by eloigabal on 06/10/2019.
 */
public interface Task<T> {
    public T execute();
    public String getTaskName();
}