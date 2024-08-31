package org.example.backendPlayground.db.mappers;

import org.apache.ibatis.annotations.Select;
import org.example.backendPlayground.domain.Post;

import java.util.List;

public interface PostMapper {
	@Select("SELECT * FROM post")
	List<Post> getAll();

	@Select("SELECT * FROM post WHERE id = #{id}")
	Post getById(long id);

	@Select("SELECT * FROM post WHERE user_id = #{id}")
	List<Post> getByUserId(long id);
}
