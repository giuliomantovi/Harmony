package com.gmantovi.harmony;

import com.gmantovi.harmony.config.StatusCode;
import com.gmantovi.harmony.gsonClasses.error.ErrorMessage;


import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ElementResponse <T, U ,D> {
/*
    private T element;
    private Class <U> messageClass;
    private U message;
    private D data;

    public ElementResponse(T element, Class<U> messageClass, U message, D data) {
        this.element = element;
        this.messageClass = messageClass;
        this.message = message;
        this.data = data;
    }

    public <T, U, D> T getElementResponse(String methodName, Map<String, Object> params, Function<U, D> function, Consumer<D> function2) {
        String response = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                methodName, params));

        Gson gson = new Gson();

        try {
            this.message = gson.fromJson(response, this.messageClass);
            //data = function.apply(this.message);
            //function2.accept(data);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /*    private <T, U, D> T getElementResponse(String methodName, Map<String, Object> params, T element, U message,
                                           D data, Function<U, D> function, Consumer<D> function2) {
        String response = null;
        message = null;

        response = MusixMatchRequest.sendRequest(MusixMatchRequest.getURLString(
                methodName, params));

        Gson gson = new Gson();

        try {
            message = (U) gson.fromJson(response, message.getClass());
            data = function.apply(message);
            function2.accept(data);
        } catch (JsonParseException jpe) {
            handleErrorResponse(response);
        }catch (Exception e){
            e.printStackTrace();
        }

        return element;
    }


    private void handleErrorResponse(String jsonResponse) {
        StatusCode statusCode;
        Gson gson = new Gson();

        ErrorMessage errMessage = gson.fromJson(jsonResponse,
                ErrorMessage.class);
        int responseCode = errMessage.getMessageContainer().getHeader()
                .getStatusCode();

        statusCode = switch (responseCode) {
            case 400 -> StatusCode.BAD_SYNTAX;
            case 401 -> StatusCode.AUTH_FAILED;
            case 402 -> StatusCode.LIMIT_REACHED;
            case 403 -> StatusCode.NOT_AUTHORIZED;
            case 404 -> StatusCode.RESOURCE_NOT_FOUND;
            case 405 -> StatusCode.METHOD_NOT_FOUND;
            default -> StatusCode.ERROR;
        };

        System.out.println("STATUS CODE: "+statusCode.getStatusCode()+", "+statusCode.getStatusMessage());
        throw new NullPointerException();
    }
    */
}
