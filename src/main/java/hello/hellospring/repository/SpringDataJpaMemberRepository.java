package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
//SpringDataJpa가 자동으로 JpaRepository를 상속받고 있는 인터페이스를 구현체로 만들어준다.
    @Override
    Optional<Member> findByName(String name);
}
