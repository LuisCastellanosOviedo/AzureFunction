package poc.functions.cloudEnvironments.quantity;

import com.microsoft.azure.serverless.functions.ExecutionContext;
import com.microsoft.azure.serverless.functions.HttpRequestMessage;
import com.microsoft.azure.serverless.functions.HttpResponseMessage;
import com.microsoft.azure.serverless.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.serverless.functions.annotation.FunctionName;
import com.microsoft.azure.serverless.functions.annotation.HttpTrigger;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by LuisCO on 5/01/2018.
 */
public class VersionManagerFolderStructure {


    public static final String SLASH="/";

    @FunctionName("folderStructure")
    public HttpResponseMessage<String> folderStructure(
            @HttpTrigger(name = "req", methods = {"get", "post"}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        String product =request.getQueryParameters().get("product");
        String channel = request.getQueryParameters().get("channel");
        String buildId = request.getQueryParameters().get("build");

        return buildResponse(request, product, channel, buildId);
    }

    private static  HttpResponseMessage<String> buildResponse(@HttpTrigger(name = "req", methods = {"get", "post"}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request, String product, String channel, String buildId) {
        if (isParameterValid(product) || isParameterValid(channel) || isParameterValid(buildId)) {
            return request.createResponse(400, "Please check the parameters ! ");
        } else {
            return request.createResponse(200, getFolderStructureFromParams(product, channel, buildId));
        }
    }


    private static String getFolderStructureFromParams(String product, String channel, String buildId) {
        return Arrays.asList(product, SLASH, channel, SLASH, buildId, SLASH).stream().collect(Collectors.joining());
    }

    private static Boolean isParameterValid(String parameter){
            return parameter == null || parameter.isEmpty();
    }


}
