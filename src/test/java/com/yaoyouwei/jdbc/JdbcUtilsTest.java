package com.yaoyouwei.jdbc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JdbcUtilsTest {
	JdbcUtils jdbcUtils;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
	}

	@After
	public void tearDown() throws Exception {
		//jdbcUtils.releaseConn();
	}

	@Test
	public void testAdd() {
		String sql = "insert into userinfo (id,username, pswd) values (?,?,?)";
		List<Object> params = new ArrayList<Object>();
		String id = UUID.randomUUID().toString();
		params.add(id);
		params.add("小张");
		params.add("123xiaozhang");
		try {
			boolean flag = jdbcUtils.updateByPstmt(sql, params);
		    assertTrue(flag);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete() throws SQLException {
		//删除名字为张三的记录
		String sql = "delete from userinfo where username = ?";
		List<Object> params = new ArrayList<Object>();
		params.add("小明");
		boolean flag = jdbcUtils.updateByPstmt(sql, params);
		assertTrue(flag);
	}
	
	@Test
	public void testUpdate() throws SQLException {
		//将名字为李四的密码改了
		String sql = "update userinfo set pswd = ? where username = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add("lisi88888");
		params.add("李四");
		boolean flag = jdbcUtils.updateByPstmt(sql, params);
		assertTrue(flag);
	}
	

	@Test
	public void testFindModeResult() throws SQLException {
		//不利用反射查询多个记录
	    String sql2 = "select * from userinfo ";
		List<Map<String, Object>> list = jdbcUtils.findModeResult(sql2, null);
		assertNotNull(list);
		System.out.println(list);
	}

	
	@Test
	public void testFindMoreRefResult() throws Exception {
		//利用反射查询多个记录
	    String sql2 = "select * from userinfo ";
		List<UserInfo> list = jdbcUtils.findMoreRefResult(sql2, null, UserInfo.class);
		assertNotNull(list);
		System.out.println(list);
	}

	@Test
	public void testFindSimpleRefResult() {
		//利用反射查询 单条记录
		String sql = "select * from userinfo where username = ? ";
		List<Object> params = new ArrayList<Object>();
		params.add("李四");
		UserInfo userInfo;
		try {
			userInfo = jdbcUtils.findSimpleRefResult(sql, params, UserInfo.class);
			System.out.print(userInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testGetObject() throws Exception{
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", "小张");
		params.put("pswd", "xiaozhang");
		UserInfo user =(UserInfo)(jdbcUtils.getObject(UserInfo.class.getName().toString(), params));
		System.out.println(user);
		
	}


}
