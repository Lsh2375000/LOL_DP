package dp.lol.lol_dp.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
/*
    BaseTimeEntity가 JPA 엔티티의 공통 매핑 정보를 포함하는 객체임을 의미한다.
    쉽게 말해 BaseTimeEntity에 선언된 필드들은 어떠한 Entity에서든 상속하여 사용할 수 있으며,
    이 어노테이션을 통해 코드 중복을 방지하고 매핑 정보를 재사용할 수 있다.
*/
public abstract class BaseTimeEntity {

    @Column(name = "created_date")
    private LocalDateTime createdDate;   // 생성일시

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;  // 최종 수정일시

    @PrePersist
    /*
        JPA Entity가 저장(Insert)되기 전에 실행할 메서드를 지정한다.
        여기서는 Entity가 DB에 최초로 생성되는 시점에 prePersist() 메서드가 호출되며,
        이를 통해 Entity의 생성일시를(created_date)를 자동으로 관리한다.
    */
    public void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    /*
        JPA Entity가 수정(Update)되기 전에 실행할 메서드를 지정한다.
        여기서는 Entity가 DB에 업데이트될 때 preUpdate() 메서드가 호출되며,
        이를 통해 Entity의 최종 수정일시(modified_date)를 자동으로 관리한다.
    */
    public void preUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }

}
