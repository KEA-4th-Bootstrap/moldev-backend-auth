package org.bootstrap.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.bootstrap.auth.common.BaseTimeEntity;
import org.bootstrap.auth.dto.request.SignUpRequestDto;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "member")
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "moldev_id", nullable = false, unique = true)
    private String moldevId;

    @Column(nullable = false)
    private String nickname;

    @Column(name = "profile_img_url", nullable = false)
    private String profileImgUrl;

    @Column(name = "island_name", nullable = false)
    private String islandName;

    @Column(name = "view_count", nullable = false)
    @Builder.Default
    private Integer viewCount = 0;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Ban ban;

    @Column(name = "marketing_agree", nullable = false)
    private Boolean isMarketingAgree;

    public static Member of(SignUpRequestDto signUpRequestDto, String encodedPassword, String profileImgUrl) {
        return Member.builder()
                .email(signUpRequestDto.email())
                .password(encodedPassword)
                .moldevId(signUpRequestDto.moldevId())
                .nickname(signUpRequestDto.nickname())
                .islandName(signUpRequestDto.islandName())
                .profileImgUrl(profileImgUrl)
                .isMarketingAgree(signUpRequestDto.isMarketingAgree())
                .build();
    }

}
