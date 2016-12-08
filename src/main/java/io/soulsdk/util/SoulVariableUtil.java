package io.soulsdk.util;

import com.google.gson.Gson;

import io.soulsdk.model.dto.User;
import io.soulsdk.model.dto.UsersLocation;

import static io.soulsdk.util.Constants.F_AVAILABLE_TILL;
import static io.soulsdk.util.Constants.F_GENDER;
import static io.soulsdk.util.Constants.F_LOCATION;
import static io.soulsdk.util.Constants.F_LOOKING_FOR;

/**
 * //
 *
 * @author Buiarov Iurii
 * @version 0.15
 * @since 05/04/16
 */
public class SoulVariableUtil {

    public static UsersLocation getUsersLocation(User user) throws Exception{
        return new Gson().fromJson(user.getParameters().getFilterableAsJson().get(F_LOCATION), UsersLocation.class);
    }

    public static Long getUsersAvailableTill(User user) throws Exception{
        return new Gson().fromJson(user.getParameters().getFilterableAsJson().get(F_AVAILABLE_TILL), Long.class);
    }

    public static String getUsersGender(User user) throws Exception{
        return new Gson().fromJson(user.getParameters().getFilterableAsJson().get(F_GENDER), String.class);
    }

    public static String getUsersTargetGender(User user) throws Exception{
        return new Gson().fromJson(user.getParameters().getFilterableAsJson().get(F_LOOKING_FOR), String.class);
    }

}