import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试利用mapper转JSON
 *
 * @author zhoupeng
 * @create time 2021-05-11-10:12
 */
public class MapperJSONTest {
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Map.class);
        ArrayList<Map> libs = new ArrayList<Map>();
        Map<String,Object> map1=new HashMap();
        map1.put("id","001");
        map1.put("name","aaa");
        libs.add(map1);
        Map<String,Object> map2=new HashMap();
        map2.put("id","002");
        map2.put("name","bbb");
        libs.add(map2);
        Map result = new HashMap();
        result.put("code", 200);
        result.put("libsInfo", libs);
        String jsonString = objectMapper.writeValueAsString(result);
        System.out.println(jsonString);//{"code":200,"libsInfo":[{"name":"aaa","id":"001"},{"name":"bbb","id":"002"}]}
    }
}
