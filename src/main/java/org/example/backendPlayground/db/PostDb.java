package org.example.backendPlayground.db;

import org.apache.ibatis.session.SqlSession;
import org.example.backendPlayground.db.mappers.PostMapper;
import org.example.backendPlayground.domain.Post;
import org.example.core.db.DbSession;

import java.util.List;

public class PostDb {

	public static List<Post> getAll() {
		try (SqlSession session = DbSession.getSession()) {
			return session.getMapper(PostMapper.class).getAll();
		}
	}

	public static Post getById(long id) {
		try (SqlSession session = DbSession.getSession()) {
			Post post = session.getMapper(PostMapper.class).getById(id);
			return post;
		}
	}

}
