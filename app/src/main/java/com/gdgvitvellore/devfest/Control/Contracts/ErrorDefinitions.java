
package com.gdgvitvellore.devfest.Control.Contracts;


/**
 * This class holds static references of all errors that occur from API requests.
 */
public class ErrorDefinitions {

    //General codes
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_TIMEDOUT = 11;
    public static final int CODE_WRONG_FORMAT = 13;

    //Authentication codes
    public static final int CODE_INVALID_CREDENTIALS = 401;
    public static final int CODE_LOGGED_IN = 200;


    /**
     * Used to get the error description for respective error code.
     * @param code error code One of {@link #CODE_SUCCESS}, {@link #CODE_TIMEDOUT},{@link #CODE_TIMEDOUT}, or {@link #CODE_WRONG_FORMAT}
     * @return
     */
    public static final String getMessage(int code){
        String mes;
        switch(code){
            case 0:
                mes="Successful Connection";
                break;
            case 11:
                mes="Session Timed Out";
                break;
            case 401:
                mes="Invalid Credentials";
                break;
            case 13:
                mes="Wrong Format";
                break;
            case 200:
                mes="Logged In";
                break;
            default:
                mes="Unknown Error";
        }
        return mes;
    }
}
