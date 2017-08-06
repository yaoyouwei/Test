package com.yaoyouwei.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class JdbcUtils {
	//数据库用户名
	private static final String USERNAME = "root";
	//数据库密码
	private static final String PASSWORD = "root";
	//驱动信息 
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	//数据库地址
	private static final String URL = "jdbc:mysql://localhost:3306/test";
	private Connection connection;
	private PreparedStatement pstmt;
	private ResultSet resultSet;
	public JdbcUtils() {
		// TODO Auto-generated constructor stub
		try{
			Class.forName(DRIVER);
			System.out.println("数据库连接成功！");

		}catch(Exception e){

		}
	}

	/**
	 * 获得数据库的连接
	 * @return
	 */
	public Connection getConnection(){
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * 释放数据库连接
	 */
	public void releaseConn(){
		if(resultSet != null){
			try{
				resultSet.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}


	/**
	 * 增加、删除、改
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public boolean updateByPstmt(String sql, List<Object>params)throws SQLException{
		boolean flag = false;
		int result = -1;
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if(params != null && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		return flag;
	}

	/**
	 * 查询单条记录
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException{
		Map<String, Object> map = new HashMap<String, Object>();
		int index  = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i=0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();//返回查询结果
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while(resultSet.next()){
			for(int i=0; i<col_len; i++ ){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		return map;
	}

	/**查询多条记录
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findModeResult(String sql, List<Object> params) throws SQLException{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while(resultSet.next()){
			Map<String, Object> map = new HashMap<String, Object>();
			for(int i=0; i<cols_len; i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}

		return list;
	}

	/**通过反射机制查询单条记录
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> T findSimpleRefResult(String sql, List<Object> params,
			Class<T> cls )throws Exception{
		T resultObject = null;
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData  = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while(resultSet.next()){
			//通过反射机制创建一个实例
			resultObject = cls.newInstance();
			for(int i = 0; i<cols_len; i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true); //打开javabean的访问权限
				field.set(resultObject, cols_value);
			}
		}
		return resultObject;

	}

	/**通过反射机制查询多条记录
	 * @param sql 
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> findMoreRefResult(String sql, List<Object> params,
			Class<T> cls )throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if(params != null && !params.isEmpty()){
			for(int i = 0; i<params.size(); i++){
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData  = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while(resultSet.next()){
			//通过反射机制创建一个实例
			T resultObject = cls.newInstance();
			for(int i = 0; i<cols_len; i++){
				String cols_name = metaData.getColumnName(i+1);
				Object cols_value = resultSet.getObject(cols_name);
				if(cols_value == null){
					cols_value = "";
				}
				Field field = cls.getDeclaredField(cols_name);
				field.setAccessible(true); //打开javabean的访问权限
				field.set(resultObject, cols_value);
			}
			list.add(resultObject);
		}
		return list;
	}
//==========================================================================================================================================

    /**执行sql语句
     * @param sql
     * @return
     * @throws SQLException
     */
    public int executeSql(String sql) throws SQLException {

        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            int result = psmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
	
    /**
     * 解析出保存对象的sql语句
     * @param object：需要保存的对象
     * @return：保存对象的sql语句
     */
    public  int getSaveObjectSql(Object object) {
        // 定义一个sql字符串
        String sql = "insert into ";
        // 得到对象的类
        Class<? extends Object> c = object.getClass();
        // 得到对象中所有的方法
        Method[] methods = c.getMethods();
        // 得到对象类的名字
        String cName = c.getName();
        // 从类的名字中解析出表名
        String tableName = cName.substring(cName.lastIndexOf(".") + 1,
                cName.length());
        sql += tableName + "(";
        List<String> mList = new ArrayList<String>();
        List<Object> vList = new ArrayList<Object>();
        for (Method method : methods) {
            String mName = method.getName();
            if (mName.startsWith("get") && !mName.startsWith("getClass")) {
                String fieldName = mName.substring(3, mName.length());
                if(fieldName.equals("Id")) break;
                mList.add(fieldName);
                try {
                    Object value = method.invoke(object, null);//将该方法的返回值返回给调用者
                    Class<?> returnType = method.getReturnType();

                    String tpye = returnType.getName();
                    switch (tpye) {
                    case "int":

                        break;
                    case "java.util.Date":
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", java.util.Locale.US);
                        Date date = sdf.parse(value.toString());
                        value=new java.sql.Date(date.getTime());
                        break;
                    case "java.lang.String":
                        value="'"+value+"'";
                        break;

                    default:
                        break;
                    }

                    vList.add(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i = 0; i < mList.size(); i++) {
            if (i < mList.size() - 1) {
                sql += mList.get(i) + ",";
            } else {
                sql += mList.get(i) + ") values(";
            }
        }
        for (int i = 0; i < vList.size(); i++) {
            if (i < vList.size() - 1) {
                sql += vList.get(i) + ",";
            } else {
                sql += vList.get(i) + ")";
            }
        }
        System.out.println("增加语句："+sql);
        int result=0;
        try {
            result= executeSql(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }



    /**根据条件删除
     * @param className
     * @param param
     * @param value
     * @return
     * @throws SQLException
     */
    public int deleteObject(String className,String param,Object value) throws SQLException {
        // 得到表名字
        String tableName = className.substring(className.lastIndexOf(".") + 1,
                className.length());
        // 根据类名来创建Class对象
        Class<?> c = null;
        try {
            c = Class.forName(className);

        } catch (ClassNotFoundException e1) {

            e1.printStackTrace();
        }
        switch (value.getClass().getName()) {
        case "class java.lang.Integer":
            value=Integer.parseInt(value.toString());
            break;

        case "java.lang.String":
            value="'"+value+"'";
            break;

        default:
            break;
        }
        String sql = "delete  from " + tableName + " where " + param +"="+value;
        System.out.println("删除sql语句：" + sql);
        int result=0;
        try {
            result= executeSql(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }



    /**查找返回单个对象
     * @param className 返回结果对象类型
     * @param param 查找条件key值
     * @param value 查找条件value值
     * @return
     * @throws SQLException
     */
    public Object getObject(String className,String param,Object value) throws SQLException {
        // 得到表名字
        String tableName = className.substring(className.lastIndexOf(".") + 1,
                className.length());
        // 根据类名来创建Class对象
        Class<?> c = null;
        try {
            c = Class.forName(className);

        } catch (ClassNotFoundException e1) {

            e1.printStackTrace();
        }
        switch (value.getClass().getName()) {
        case "class java.lang.Integer":
            value=Integer.parseInt(value.toString());
            break;

        case "java.lang.String":
            value="'"+value+"'";
            break;

        default:
            break;
        }
        String sql = "select * from " + tableName + " where " + param +"="+value;
        System.out.println("查找sql语句：" + sql);
        //Connection con = JdbcUtils_C3P0.getConnection();
        // 创建类的实例
        Object obj = null;
        try {

            Statement stm = connection.createStatement();
            // 得到执行查寻语句返回的结果集
            ResultSet rs = stm.executeQuery(sql);
            // 得到对象的方法数组
            Method[] methods = c.getMethods();
            // 遍历结果集
            while (rs.next()) {

                obj = c.newInstance();
                for (Method method : methods) {
                    String methodName = method.getName();
                    // 如果对象的方法以set开头
                    if (methodName.startsWith("set")) {
                        // 根据方法名字得到数据表格中字段的名字
                        String columnName = methodName.substring(3,
                                methodName.length());
                        // 得到方法的参数类型
                        Class[] parmts = method.getParameterTypes();
                        if (parmts[0] == String.class) {
                            // 如果参数为String类型，则从结果集中按照列名取得对应的值，并且执行改set方法
                            method.invoke(obj, rs.getString(columnName));
                        }
                        if (parmts[0] == int.class) {
                            method.invoke(obj, rs.getInt(columnName));
                        }
                        if (parmts[0] == Date.class) {
                            method.invoke(obj, rs.getDate(columnName));
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 查找 返回对象集合
     * @param className 返回结果对象类型
     * @param param 查找条件key值
     * @param value 查找条件value值
     * @return
     * @throws SQLException
     */
    public List<Object> getObjectList(String className,String param,Object value) throws SQLException {
        // 得到表名字
        String tableName = className.substring(className.lastIndexOf(".") + 1,
                className.length());
        // 根据类名来创建Class对象
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        switch (value.getClass().getName()) {
        case "class java.lang.Integer":
            value=Integer.parseInt(value.toString());
            break;

        case "java.lang.String":
            value="'"+value+"'";
            break;

        default:
            break;
        }
        String sql = "select * from " + tableName + " where " + param +"="+value;
        System.out.println("查找sql语句：" + sql);
        List<Object> list = null;
        // 创建类的实例
        try {

            Statement stm = connection.createStatement();
            // 得到执行查寻语句返回的结果集
            ResultSet rs = stm.executeQuery(sql);
            // 得到对象的方法数组
            Method[] methods = c.getMethods();
            list=new ArrayList<>();
            // 遍历结果集
            while (rs.next()) {
                Object  obj = c.newInstance();
                for (Method method : methods) {
                    String methodName = method.getName();
                    // 如果对象的方法以set开头
                    if (methodName.startsWith("set")) {
                        // 根据方法名字得到数据表格中字段的名字
                        String columnName = methodName.substring(3,
                                methodName.length());
                        // 得到方法的参数类型
                        Class[] parmts = method.getParameterTypes();
                        if (parmts[0] == String.class) {
                            // 如果参数为String类型，则从结果集中按照列名取得对应的值，并且执行改set方法
                            method.invoke(obj, rs.getString(columnName));
                        }
                        if (parmts[0] == int.class) {
                            method.invoke(obj, rs.getInt(columnName));
                        }
                        if (parmts[0] == Date.class) {
                            method.invoke(obj, rs.getDate(columnName));
                        }
                    }
                }
                list.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**根据多条件查找返回单个对象，多条件返回多个对象同理
     * @param className 返回结果对象类型
     * @param param 查找条件key值
     * @param value 查找条件value值
     * @return
     * @throws SQLException
     * 20160215新增
     */
    //@Override
    public Object getObject(String className,Map<String, Object> params) throws SQLException {
        // 得到表名字
        String tableName = className.substring(className.lastIndexOf(".") + 1,
                className.length());
        // 根据类名来创建Class对象
        Class<?> c = null;
        try {
            c = Class.forName(className);

        } catch (ClassNotFoundException e1) {

            e1.printStackTrace();
        }
        String sql;
        if(params!=null&&params.size()>0){

            Iterator<Entry<String, Object>> it=params.entrySet().iterator();       
            StringBuffer sbf=new StringBuffer(" where ");
            while(it.hasNext()){
                Map.Entry<String, Object> entry = it.next();          
                String key = entry.getKey().toString();          
                Object value = entry.getValue();          
                System.out.println(key+" ==== "+value+"  "+value.getClass().getName());                    

                switch (value.getClass().getName()) {
                case "java.lang.Integer":
                    value=Integer.parseInt(value.toString());
                    sbf.append(key+"="+value+" and ");
                    break;

                case "java.lang.String":
                    value="'"+value+"'";
                    sbf.append(key+"="+value+" and ");
                    break;

                default:
                    break;
                }
            }
            sbf.delete(sbf.length()-4, sbf.length());
            sql = "select * from " + tableName + sbf.toString();
        }else{
            sql = "select * from " + tableName;
        }
        System.out.println("查找sql语句：" + sql);
        // 创建类的实例
        Object obj = null;
        try {

            Statement stm = connection.createStatement();
            // 得到执行查寻语句返回的结果集
            ResultSet rs = stm.executeQuery(sql);
            // 得到对象的方法数组
            Method[] methods = c.getMethods();
            // 遍历结果集
            while (rs.next()) {
                obj = c.newInstance();
                for (Method method : methods) {
                    String methodName = method.getName();
                    // 如果对象的方法以set开头
                    if (methodName.startsWith("set")) {
                        // 根据方法名字得到数据表格中字段的名字
                        String columnName = methodName.substring(3,
                                methodName.length());
                        // 得到方法的参数类型
                        Class<?>[] parmts = method.getParameterTypes();
                        if (parmts[0] == String.class) {
                            // 如果参数为String类型，则从结果集中按照列名取得对应的值，并且执行改set方法
                            method.invoke(obj, rs.getString(columnName));
                        }
                        if (parmts[0] == int.class) {
                            method.invoke(obj, rs.getInt(columnName));
                        }
                        if (parmts[0] == Date.class) {
                            method.invoke(obj, rs.getDate(columnName));
                        }
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();

	}

}
