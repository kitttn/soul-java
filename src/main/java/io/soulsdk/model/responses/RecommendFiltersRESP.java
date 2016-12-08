package io.soulsdk.model.responses;

import com.google.gson.JsonObject;

/**
 * //
 *
 * @author Buiarov Iurii
 * @version 0.15
 * @since 13/05/16
 */
public class RecommendFiltersRESP {

    private JsonObject filter;


    public JsonObject getFilter() {
        return filter;
    }

    public void setFilter(JsonObject filter) {
        this.filter = filter;
    }
}