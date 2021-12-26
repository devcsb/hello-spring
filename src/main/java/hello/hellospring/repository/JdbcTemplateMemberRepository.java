package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    //    @Autowired // 생성자가 단 하나일 경우, @Autowired 어노테이션 생략 가능
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate); // 쿼리를 만들어주는 SimpleJdbcInsert
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id"); //테이블명과 pk를 입력받는다.

        Map<String, Object> parameters = new HashMap<>(); // 던져줄 값을 HashMap에 담는다.
        parameters.put("name", member.getName()); //넣을 값의 컬럼명과 그 값을 parameters에 담는다.

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); //파라미터가 대입된 sql문을 실행하고 key 변수에 key값(id값)을 돌려받는다.
        member.setId(key.longValue()); //member 객체에 id값 넣는다.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id); //쿼리 날리고 결과를 RowMapper통해서 맵핑하고 그 결과를 List로 받는다.
        return result.stream().findAny(); //옵셔널로 바꿔서 반환.
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
