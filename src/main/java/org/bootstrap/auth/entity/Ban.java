package org.bootstrap.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "ban")
@Entity
public class Ban {

    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime unbanDate;
    private ReasonType reason;

    public static Ban of(Member member, LocalDateTime unbanDate) {
        return Ban.builder()
                .member(member)
                .unbanDate(unbanDate)
                .build();
    }
}
