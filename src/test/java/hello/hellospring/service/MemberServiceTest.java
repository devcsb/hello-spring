package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.repository.MemoryMemberRepositoryTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    /*새로 선언하였으므로, MemberService에서 선언한 MemoryMemberRepository와
    MemberServiceTest에서 쓰는 MemoryMemberRepository가 서로 다른 객체인 상태.*/
//    MemberService memberService = new MemberService();
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach //각 테스트를 실행하기 전에
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository(); //MemoryMemberRepository 생성해서
        memberService = new MemberService(memberRepository); // MemberService에 주입.(Dependency Injection)
    }

    @AfterEach
    public void atferEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

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