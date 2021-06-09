import java.util.ArrayList;
import java.util.List;

/**
 * 删除有序数组中的重复元素
 *
 * @author zhoupeng
 * @create time 2021-05-15-16:25
 */
public class RemoveDulpicateElement {
    public static void main(String[] args) {
        int[] nums={2,4,5,3,3,2};
        /*List<Integer> result = new ArrayList<Integer>();
        boolean flag;
        for (int i = 0; i <nums.length ; i++) {
            flag = false;
            for(int j = 0; j <result.size() ; j++){
                if(nums[i]==result.get(j)){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                result.add(nums[i]);
            }
        }
        int[] arr=new int[result.size()];
        int j = 0;
        for (Integer integer : result) {

            arr[j] = integer;
            j++;
            System.out.println(integer);
        }*/
        System.out.println("---------------------------------------");

        List<Integer> list = new ArrayList<Integer>();
        int count = 0;
        for (int m = 0; m < nums.length; m++) {
            for (int n = m + 1; n < nums.length; n++) {
                if (nums[m] == nums[n]) {
                    count++;
                }
            }
            if (count == 1) {
                list.add(nums[m]);
            }
            count = 0;
        }
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }






}
