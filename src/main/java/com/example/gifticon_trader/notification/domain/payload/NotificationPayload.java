package com.example.gifticon_trader.notification.domain.payload;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FcmPayload.class, name = "FCM"),
        @JsonSubTypes.Type(value = EmailPayload.class, name = "EMAIL"),
        @JsonSubTypes.Type(value = SocketPayload.class, name = "SOCKET"),
        @JsonSubTypes.Type(value = KakaoPayload.class, name = "KAKAO")
})
public abstract class NotificationPayload {
}
