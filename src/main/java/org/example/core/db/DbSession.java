package org.example.core.db;

import org.example.core.configuration.Configuration;
import org.example.core.configuration.Props;
import org.aeonbits.owner.ConfigCache;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbSession {
	private final static String resource = "mybatis-config.xml";
	private static SqlSessionFactory sqlSessionFactory;
	private final static Props props = ConfigCache.getOrCreate(Props.class);

	public static SqlSessionFactory getSqlSessionFactory() {
		if (sqlSessionFactory == null) {
			InputStream inputStream;
			try {
				inputStream = Resources.getResourceAsStream(resource);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			if (props.envId().getValue() == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			} else {

				Properties properties = new Properties();
				properties.setProperty("DB_URL", Configuration.props.dbUrl());
				properties.setProperty("DB_USER", Configuration.props.dbUser());
				properties.setProperty("DB_PASSWORD", Configuration.props.dbPassword());

				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
			}
		}
		return sqlSessionFactory;
	}

	public static SqlSession getSession() {
		return getSqlSessionFactory().openSession();
	}

}
