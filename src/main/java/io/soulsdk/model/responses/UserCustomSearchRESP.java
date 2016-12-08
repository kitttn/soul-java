package io.soulsdk.model.responses;

import java.util.List;

import io.soulsdk.model.dto.Meta;
import io.soulsdk.model.dto.User;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain list of Custom Search results
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class UserCustomSearchRESP extends GeneralResponse {

    private List<User> users;
    private Meta _meta;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Meta get_meta() {
        return _meta;
    }

    public void set_meta(Meta _meta) {
        this._meta = _meta;
    }


}