package dp.lol.lol_dp.member.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
/*
    BaseTimeEntity가 JPA 엔티티의 공통 매핑 정보를 포함하는 객체임을 의미한다.
    쉽게 말해 BaseTimeEntity에 선언된 필드들은 어떠한 Entity에서든 상속하여 사용할 수 있으며,
    이 어노테이션을 통해 코드 중복을 방지하고 매핑 정보를 재사용할 수 있다.
*/
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "createdDate", updatable = false)
    private String createdDate;   // 생성일시
    @Column(name = "modifiedDate")
    private String modifiedDate;  // 최종 수정일시



    @PrePersist
    public void prePersist() {
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

}
