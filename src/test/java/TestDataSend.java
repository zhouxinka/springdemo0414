/**
 * 测试数据推送
 *
 * @author zhoupeng
 * @create time 2021-05-17-20:01
 */
public class TestDataSend {


    public static void main(String[] args) {
        DataSend dataSendToMysql = new DataSendToMysql();
        DataSend dataSendToQNS = new DataSendToQNS();
        dataSendToQNS.executeDataSend();
        System.out.println("-----------------------------------------");
        dataSendToMysql.executeDataSend();

    }
}
