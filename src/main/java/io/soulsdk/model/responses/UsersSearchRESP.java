package io.soulsdk.model.responses;

import java.util.List;

import io.soulsdk.model.dto.RecommendMeta;
import io.soulsdk.model.dto.User;
import io.soulsdk.model.general.GeneralResponse;

/**
 * HTTP-response body object contain list of Recommended Search results
 *
 * @author Buiarov Uirii
 * @version 0.15
 * @since 28/03/16
 */
public class UsersSearchRESP extends GeneralResponse {

    private List<User> users;
    private RecommendMeta _meta;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public RecommendMeta getMeta() {
        return _meta;
    }

    public void setMeta(RecommendMeta _meta) {
        this._meta = _meta;
    }


}