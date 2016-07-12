package httpClient;

import java.util.HashMap;
import java.util.Map;

import static httpClient.myhttp.sendGet;

/**
 * @author 付施威
 * @version V1.0
 * @SystemName UTB-CLOUD
 * @ModuleName httpClient
 * @Date 16/7/12上午11:17
 * @Description 描述
 */
public class myPost {

    public static void main(String[] args) {


        /**
         * 主函数，测试请求
         *
         * @param args
         */

//        for(int i= 0 ; i <100;i++){

            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("busId", "85");
            parameters.put("fromDate", "06/27/2016");
            parameters.put("fromTime", "00:00");
            parameters.put("toDate", "07/11/2016");
//            parameters.put("toDate", "07/01/2016");
            parameters.put("toTime", "23:00");

            String result = sendGet("",
                    parameters);
//        }



//           System.out.println(result);

    }


}
