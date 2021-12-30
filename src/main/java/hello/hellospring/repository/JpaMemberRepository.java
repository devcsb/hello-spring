package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    //jpa를 쓰려면 항상 트랜잭션이 있어야한다. 서비스계층에 @Transactional 어노테이션 삽입

    private final EntityManager em; // JPA를 쓰려면 EntityManager를 주입받아야한다.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Member save(Member member) {
        em.persist(member); // jpa가 insert 쿼리 만들어서 집어넣고, member.setId()로 id값 또한 Member객체에 넣는다.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); //find(조회할 타입, 식별자pk값)
        return Optional.ofNullable(member);
    }

    //findByName이나 findAll 처럼 pk 기반이 아닌 것들은 jpql을 작성해주어야한다.
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {//ctrl + alt +n => inline variable
        return em.createQuery("select m from Member m", Member.class) //Entity를 대상으로 쿼리를 날리는 jpql
                .getResultList();
        //select m from Member m 에서 Member는 대상 Entity. select의 대상은 Member Entity 자체. 소문자 m은 Member의 alias다.
    }
}
