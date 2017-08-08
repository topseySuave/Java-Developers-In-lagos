package apps.gabrielmicah.githubdevs;

import android.graphics.Color;

/**
 * Created by Daniel on 7/23/2016.
 */
public class DevData
{

    public String username;
    public String image,profile_url,html_url;

    public DevData(String user, String profilepic,String url,String html_url)
    {

        this.username = user;
        this.image = profilepic;
        this.profile_url = url;
        this.html_url = html_url;
    }
}
