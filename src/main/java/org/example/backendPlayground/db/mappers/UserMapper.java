package org.example.backendPlayground.db.mappers;

import org.example.backendPlayground.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
	@Select("SELECT * FROM user")
	List<User> getAll();

	@Select("SELECT * FROM user WHERE id = #{id}")
	User getById(long id);
}
