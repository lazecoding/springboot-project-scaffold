package lazecoding.project.plugins.batch;


/**
 * @author lazecoding
 */
public class BatchMain {
    public static void main(String[] args) {
        // 注册 BatchExecutor
        BatchExecutor.registered();

        // 处理
        BatchResponseModel batchResponseModel = new BatchResponseModel("type", "requestContext");
        BatchExecutor.offer(new BatchRequestBean("111", batchResponseModel).addResponseModel(batchResponseModel).addResponseModel(batchResponseModel));



        if (BatchExecutor.isEmpty()) {
            System.out.println("BatchExecutor.isEmpty()");
        }
        System.out.println("BatchExecutor  End.");

    }
}
