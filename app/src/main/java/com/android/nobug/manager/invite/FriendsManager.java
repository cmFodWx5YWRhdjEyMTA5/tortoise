package com.android.nobug.manager.invite;

import android.app.Activity;

import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

/**
 * Created by rrobbie on 2017. 4. 17..
 */

public class FriendsManager {


    //  =====================================================================================

    public static void invite(Activity activity) {
        String appLinkUrl, previewImageUrl;

        appLinkUrl = "https://www.mydomain.com/myapplink";
        previewImageUrl = "https://www.mydomain.com/my_invite_image.jpg";

        if (AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();
            AppInviteDialog.show(activity, content);
        }


    }

}


