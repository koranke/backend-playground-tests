package org.example.backendPlayground.db;

import org.example.backendPlayground.db.mappers.UserMapper;
import org.example.backendPlayground.domain.User;
import org.example.core.db.DbSession;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserDb {

	public static List<User> getAll() {
		try (SqlSession session = DbSession.getSession()) {
			return session.getMapper(UserMapper.class).getAll();
		}
	}

	public static User getById(long id) {
		try (SqlSession session = DbSession.getSession()) {
			return session.getMapper(UserMapper.class).getById(id);
		}
	}

}
