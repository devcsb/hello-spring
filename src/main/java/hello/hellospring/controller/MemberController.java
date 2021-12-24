package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    //    private final MemberService memberService = new MemberService(); // new로 매번 생성해서 똑같은 기능을 여러개의 객체로 나눠 쓸 필요가 없다.
    private final MemberService memberService;

//    @Autowired private MemberService memberService; 필드 주입 방식

    /*
          setter 주입 방식. 단점: 누군가 MemberController를 호출했을 때 setter가 public으로 열려있어야 하므로, 계속 public 상태로 노출되어 있는 문제가 생김.
        private MemberService memberService
        @Autowired
        public void setMemberService(MemberService memberService) {
            this.memberService = memberService;
        }
    */
    @Autowired //MemberController가 생성될 때 스프링 빈에 있는 Service를 가져다가 넣어준다. (Dependency Injection)
    public MemberController(MemberService memberService) { //생성자 주입 방식. 처음 애플리케이션이 조립될 때 한 번 세팅되고 그 다음부터 접근할 수 없음)
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
