package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
//    private final MemberService memberService = new MemberService(); // new로 매번 생성해서 똑같은 기능을 여러개의 객체로 나눠 쓸 필요가 없다.
    private final MemberService memberService;

    @Autowired //MemberController가 생성될 때 스프링 빈에 있는 Service를 가져다가 넣어준다. (Dependency Injection)
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
