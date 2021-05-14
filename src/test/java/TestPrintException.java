import org.apache.log4j.Logger;

/**
 * 测试使用日志打印异常信息
 *
 * @author zhoupeng
 * @create time 2021-05-12-10:38
 */
public class TestPrintException {
    private static Logger log = Logger.getLogger(TestPrintException.class);

    public static void main(String[] args) {
        try{
            // 模拟空指针异常
            int[] array = {1,2,3,4,5};
            int outBoundInt = array[5];
        }catch (Exception e){
            System.out.println("使用e打印异常信息");
            log.error(e);

            System.out.println("使用e.getMessage()打印异常信息");
            //打印创建异常对象时传入的message信息
            log.error(e.getMessage());

            System.out.println("使用e.toString()打印异常信息");
            //打印异常的全限定名称和创建异常对象时传入的message信息
            log.error(e.toString());


            System.out.println("使用log.error(String,Throwable)打印异常信息");
            log.error("程序异常：",e);//建议使用这种，能定位到具体哪一行代码
        }
    }
}
