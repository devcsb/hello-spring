package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService { // 테스트 코드 작성 단축키 : ctrl + shift + t

    @Autowired  //MemberService가 생성될 때 스프링 컨테이너에 있는 memberRepository를 가져와서 넣어준다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복회원 가입불가

////        Member member1 = result.get(); // 곧바로 get으로 꺼내도 되지만 권장하지 않는다.
////        result.orElseGet() // 비어있는 Optional 객체에 대해서, 넘어온 함수형 인자를 통해 생성된 객체를 반환

//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> { //result에 값이 있을때
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });
        //아래 처럼 리팩토링하여 가독성 개선

//        memberRepository.findByName(member.getName())
//                .ifPresent(m -> { //result에 값이 있을때
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });
        //중복이 예상되는 기능이므로
        //ctrl + shift + alt + t(refactor this) -> ctrl + alt + M(Extract method) 로 별도의 메서드로 따로 빼낸다.

        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }


    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { //result에 값이 있을때
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public  Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
