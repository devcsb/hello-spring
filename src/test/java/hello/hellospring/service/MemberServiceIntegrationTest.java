package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest //스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional    //테스트를 시작 전, 트랜잭션을 실행하고, 테스트가 끝나면 rollback 시킨다. 각각의 테스트마다 적용. 테스트클래스에서만 롤백이 적용됨.
class MemberServiceIntegrationTest { //통합테스트
    //컨테이너 없이 순수 자바코드로 작성한 단위테스트로 테스트하는 것이 훨씬 좋은 방법이다.
    //컨테이너 띄우고 하는 테스트는 일반적으로 통합테스트라 부른다.

    //테스트이므로 필드 주입을 해도 무방하다.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository; //SpringConfig에서 정의한 구현체가 호출된다.

    @Test
//    @Commit   // db에 Commit 해라는 어노테이션. @Transactional이 있어도 @Commit 어노테이션이 있으면 바로 db에 commit 한다.
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

//        Hibernate: select member0_.id as id1_0_, member0_.name as name2_0_ from member member0_ where member0_.name=?
//        Hibernate: insert into member (id, name) values (null, ?)

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
//        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        //  () -> memberService.join(member2) 이 로직이 실행됐을 때 IllegalStateException이 실행된다면 성공. 이 방법이 한 눈에 안들어오므로,

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2)); //메시지를 변수로 받아서
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


/*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}