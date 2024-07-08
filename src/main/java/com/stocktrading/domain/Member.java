package com.stocktrading.domain;

import com.stocktrading.global.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private String id;

    private String password;

    private String nickName;
    
    public Member(String memberId, String password, String nickName) {
            this.memberId = memberId;
            this.password = password;
            this.nickName = nickName;
        }

        public boolean login(String password) {
            return this.password.equals(password);
    }

}