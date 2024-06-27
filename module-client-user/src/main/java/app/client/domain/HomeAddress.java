package app.client.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeAddress {

    @Column(name = "address")
    private String addressInfo;
    @Column(name = "detail_address")
    private String detailAddress;
    @Column(name = "zip_code")
    private String zipCode;

    private HomeAddress(String zipCode, String addressInfo, String detailAddress) {
        this.zipCode = zipCode;
        this.addressInfo = addressInfo;
        this.detailAddress = detailAddress;
    }

    public static HomeAddress create(String zipCode, String addressInfo, String detailAddress) {
        return new HomeAddress(zipCode, addressInfo, detailAddress);
    }
}
