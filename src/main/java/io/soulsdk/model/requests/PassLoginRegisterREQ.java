package io.soulsdk.model.requests;

/**
 * //
 *
 * @author Buiarov Iurii
 * @version 0.15
 * @since 22/04/16
 */
public class PassLoginRegisterREQ {

    private String login;
    private String passwd;
    private String apiKey;

    public PassLoginRegisterREQ(String login, String passwd, String apiKey) {
        this.login = login;
        this.passwd = passwd;
        this.apiKey = apiKey;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
