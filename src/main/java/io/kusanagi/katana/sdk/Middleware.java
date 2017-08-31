/*
 * Java 8 SDK for the KATANA(tm) Platform (http://katana.kusanagi.io)
 * Copyright (c) 2016-2017 KUSANAGI S.L. All rights reserved.
 *
 * Distributed under the MIT license
 *
 * For the full copyright and license information, please view the LICENSE
 *  file that was distributed with this source code
 *
 * @link      https://github.com/kusanagi/katana-sdk-java8
 * @license   http://www.opensource.org/licenses/mit-license.php MIT License
 * @copyright Copyright (c) 2016-2017 KUSANAGI S.L. (http://kusanagi.io)
 *
 */

package io.kusanagi.katana.sdk;

import io.kusanagi.katana.api.Api;
import io.kusanagi.katana.api.commands.Mapping;
import io.kusanagi.katana.api.commands.RequestCommandPayload;
import io.kusanagi.katana.api.commands.ResponseCommandPayload;
import io.kusanagi.katana.api.commands.common.CommandPayload;
import io.kusanagi.katana.api.component.Component;
import io.kusanagi.katana.api.component.Constants;
import io.kusanagi.katana.api.replies.CallReplyPayload;
import io.kusanagi.katana.api.replies.ResponseReplyPayload;
import io.kusanagi.katana.api.replies.common.CommandReplyResult;
import io.kusanagi.katana.api.serializers.ActionEntity;
import io.kusanagi.katana.api.serializers.HttpResponseEntity;
import io.kusanagi.katana.api.serializers.RequestEntity;
import io.kusanagi.katana.api.serializers.ResponseEntity;

import java.io.IOException;

/**
 * Created by juan on 27/08/16.
 */
public class Middleware extends Component<Api, CommandReplyResult, Middleware> {

    private Callable<Request> requestCallable;

    private Callable<Response> responseCallable;

    public Middleware(String[] args) {
        super(args);
    }

    /**
     * take a function, which SHOULD be used to process the request Middleware logic in the userland source file. The
     * instance of the Middleware object SHOULD be returned.
     * <p>
     * An instance of the Request class MUST be provided as the first argument of the callback function, while the value
     * returned by the callback function MAY be either the instance of the Request class passed to the function, or an
     * instance of the ResponseEntity class, generated by the Request.new_response() function. The return of any other value,
     * the absence of a return value or the raising of an exception MUST be treated as an error.
     *
     * @param callable function to process a request middleware logic
     * @return Return the instance of the middleware
     */
    public Middleware request(Callable<Request> callable) {
        this.requestCallable = callable;
        return this;
    }

    /**
     * take a function, which SHOULD be used to process the response Middleware logic in the userland source file. The
     * instance of the Middleware object SHOULD be returned.
     * <p>
     * An instance of the ResponseEntity class MUST be provided as the first argument of the callback function, while the
     * value returned by the callback function MUST be the instance of the ResponseEntity class passed to the function. The
     * return of any other value, the absence of a return value or the raising of an exception MUST be treated as an
     * error.
     *
     * @param callable function to process a response middleware logic
     * @return Return the instance of the middleware
     */
    public Middleware response(Callable<Response> callable) {
        this.responseCallable = callable;
        return this;
    }

    @Override
    protected Api getSdkCommand(String componentType, Mapping mappings, byte[] commandBytes) throws IOException {
        if (componentType.equals(Constants.REQUEST_STRING)) {
            CommandPayload<RequestEntity> command = serializer.deserialize(commandBytes, RequestCommandPayload.class);
            RequestEntity requestEntity = command.getCommand().getArgument();
            return new Request.Builder()
                    .setRequestEntity(requestEntity)
                    .setComponent(this)
//                    .setPath(requestEntity.getTransport().getMeta().getGateway().get(1))
                    .setName(this.getName())
                    .setVersion(this.getVersion())
                    .setPlatformVersion(this.getFrameworkVersion())
                    .setVariables(this.getVar())
                    .setDebug(this.isDebug())
                    .setMapping(mappings)
                    .build();
        } else {
            CommandPayload<ResponseEntity> command = serializer.deserialize(commandBytes, ResponseCommandPayload.class);
            ResponseEntity responseEntity = command.getCommand().getArgument();
            return new Response.Builder()
                    .setResponseEntity(responseEntity)
                    .setComponent(this)
                    .setPath(responseEntity.getTransport().getMeta().getGateway().get(1))
                    .setName(this.getName())
                    .setVersion(this.getVersion())
                    .setPlatformVersion(this.getFrameworkVersion())
                    .setVariables(this.getVar())
                    .setDebug(this.isDebug())
                    .setMapping(mappings)
                    .build();
        }
    }

    @Override
    protected Api getSdkCommand(String componentType, Mapping mappings, String jsonCommand) throws IOException {
        if (componentType.equals(Constants.REQUEST_STRING)) {
            CommandPayload<RequestEntity> command = serializer.deserialize(jsonCommand, RequestCommandPayload.class);
            RequestEntity requestEntity = command.getCommand().getArgument();
            return new Request.Builder()
                    .setRequestEntity(requestEntity)
                    .setComponent(this)
//                    .setPath(requestEntity.getTransport().getMeta().getGateway().get(1))
                    .setName(this.getName())
                    .setVersion(this.getVersion())
                    .setPlatformVersion(this.getFrameworkVersion())
                    .setVariables(this.getVar())
                    .setDebug(this.isDebug())
                    .setMapping(mappings)
                    .build();
        } else {
            CommandPayload<ResponseEntity> command = serializer.deserialize(jsonCommand, ResponseCommandPayload.class);
            ResponseEntity responseEntity = command.getCommand().getArgument();
            return new Response.Builder()
                    .setResponseEntity(responseEntity)
                    .setComponent(this)
                    .setPath(responseEntity.getTransport().getMeta().getGateway().get(1))
                    .setName(this.getName())
                    .setVersion(this.getVersion())
                    .setPlatformVersion(this.getFrameworkVersion())
                    .setVariables(this.getVar())
                    .setDebug(this.isDebug())
                    .setMapping(mappings)
                    .build();
        }
    }

    @Override
    protected CommandReplyResult getCommandReplyPayload(String componentType, Api response) {
        if (componentType.equals(Constants.REQUEST_STRING)) {
            CallReplyPayload commandReplyPayload = new CallReplyPayload();
            CallReplyPayload.CallCommandReply callCommandReply = new CallReplyPayload.CallCommandReply();
            CallReplyPayload.CallResult callResult = new CallReplyPayload.CallResult();
            callResult.setRequestCall((RequestCall) getReply(componentType, response));
            callCommandReply.setName(getName());
            callCommandReply.setResult(callResult);
            commandReplyPayload.setCommandReply(callCommandReply);
            return commandReplyPayload;
        } else {
            ResponseReplyPayload commandReplyPayload = new ResponseReplyPayload();
            ResponseReplyPayload.ResponseCommandReply responseCommandReply = new ResponseReplyPayload.ResponseCommandReply();
            ResponseReplyPayload.ResponseResult responseResult = new ResponseReplyPayload.ResponseResult();

            HttpResponse httpResponse = (HttpResponse) getReply(componentType, response);

            HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
            httpResponseEntity.setHeaders(httpResponse.getHeaders());
            httpResponseEntity.setStatus(httpResponse.getStatus());
            httpResponseEntity.setProtocolVersion(httpResponse.getProtocolVersion());
            httpResponseEntity.setBody(httpResponse.getBody());

            responseResult.setHttpResponse(httpResponseEntity);
            responseCommandReply.setName(getName());
            responseCommandReply.setResult(responseResult);
            commandReplyPayload.setCommandReply(responseCommandReply);
            return commandReplyPayload;
        }
    }

    @Override
    protected CommandReplyResult getReply(String componentType, Api response) {
        return componentType.equals(Constants.REQUEST_STRING) ? ((Request) response).getRequestCall() : ((Response) response).getHttpResponse();
    }

    @Override
    protected byte[] getReplyMetadata(CommandReplyResult reply) {
        return new byte[0];
    }

    @Override
    protected Callable getCallable(String componentType) {
        return componentType.equals(Constants.REQUEST_STRING) ? requestCallable : responseCallable;
    }

    @Override
    public void run() {
        if (this.startupCallable != null) {
            this.startupCallable.run(this);
        }

        super.run();
    }

    @Override
    protected void runShutdown() {
        if (this.shutdownCallable != null) {
            this.shutdownCallable.run(this);
        }
    }

    @Override
    protected void runErrorCallback() {
        if (this.errorCallable != null) {
            this.errorCallable.run(this);
        }
    }
}
