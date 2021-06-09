/**
 * 数据推送的抽象类
 *
 * @author zhoupeng
 * @create time 2021-05-17-19:54
 */
public abstract class DataSend {
    public void  executeDataSend(){
        System.out.println("父类的executeDataSend方法...");
        executeDataSendAction();
    }
    abstract void executeDataSendAction();
}
