package io.soulsdk.util;
/*

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.soulsdk.model.dto.UsersLocation;

*/
/**
 * //
 *
 * @author Buiarov Iurii
 * @version 0.15
 * @since 26/09/16
 *//*


public class GeoUtil {

    public static String getCity(Context context, UsersLocation usersLocation) {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(usersLocation.getLat(), usersLocation.getLng(), 1);
            if (addresses.size() > 0)
                return addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
*/
