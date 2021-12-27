package hello.hellospring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // JPA가 관리하는 엔티티라고 선언.
public class Member {
    // DB가 id를 자동으로 생성해주는 것을 아이덴티티라고 부른다. 아이덴티티 전략.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "username") // 만약 db의 컬럼명이 username이라면 이렇게 어노테이션을 달아준다.
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
