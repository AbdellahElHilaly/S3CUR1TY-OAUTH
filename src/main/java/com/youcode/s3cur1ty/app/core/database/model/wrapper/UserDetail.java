package com.youcode.s3cur1ty.app.core.database.model.wrapper;

import com.youcode.s3cur1ty.security.provider.google.common.data.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDetail {
    String name;
    String given_name;
    String family_name;
    String picture;
    String email;
    boolean email_verified;
    String locale;

    public static UserDetail fromUserInfo(UserInfo userInfo) {
        return UserDetail.builder()
                .name(userInfo.name())
                .given_name(userInfo.given_name())
                .family_name(userInfo.family_name())
                .picture(userInfo.picture())
                .email(userInfo.email())
                .email_verified(userInfo.email_verified())
                .locale(userInfo.locale())
                .build();
    }
}
