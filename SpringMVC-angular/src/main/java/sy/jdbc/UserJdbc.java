package sy.jdbc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class UserJdbc extends JdbcDaoSupport{
	/**
	 * 把jdbcTemplate注入进去
	 * @param jb
	 */
	@Resource
	public void setJb(JdbcTemplate jb) {
	 super.setJdbcTemplate(jb);
	}
	
	public List<Map<String, Object>> queryList () {
		String sql="select * from user";
		return this.getJdbcTemplate().queryForList(sql);
	}
}
