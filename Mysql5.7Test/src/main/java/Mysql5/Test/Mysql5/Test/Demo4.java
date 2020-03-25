package Mysql5.Test.Mysql5.Test;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Date;


public class Demo4 {

public static void main(String[] args) throws ClassNotFoundException, SQLException {

//	final String url = "jdbc:mysql://127.0.0.1/teacher" ;
	
	final String name = "com.mysql.jdbc.Driver" ;
	
	final String url = "jdbc:mysql://192.168.2.201:3307/test?characterEncoding=utf-8&serverTimezone=UTC" ;
//	
//	final String name = "com.mysql.cj.jdbc.Driver" ;
	
	final String user = "root" ;
	
	final String password = "root" ;
	
	Connection conn = null ;
	
	Class.forName(name); //指定连接类型
	
	conn = DriverManager.getConnection(url, user, password); //获取连接
	
	if (conn!= null ) {
	
	System.out.println( "获取连接成功" );
	
	insert(conn);
	
	} else {
	
	System.out.println( "获取连接失败" );
	
	}
	
	}
	
	public static void insert(Connection conn) {
	
	// 开始时间
	
	Long begin = new Date().getTime();
	
	// sql前缀
	
//	String prefix = "INSERT INTO analog_config (id,NAME,device_second_id,address,"
//				+ "refname,fc,device_primary_id,analog_type_id,unit_id,decimal_digit,"
//				+ "modulus,OFFSET,zero_value,one_up,two_up,one_low,two_low,back,artificial_set,"
//				+ "artificial_value,STORAGE,unreal,refresh,one_judge,two_judge,one_up_lvl,one_up_sound,"
//				+ "one_low_lvl,one_low_sound,two_up_lvl,two_up_sound,two_low_lvl,two_low_sound,v_up_lvl,"
//				+ "v_up_sound,v_low_lvl,v_low_sound,description,refresh_deadband,max_min_value,MAX_VALUE,"
//				+ "min_value,gradient_limit,gradient_limit_value,zero_value_set,ref_id,change_save,"
//				+ "integral_save,save_unit,sortnum) VALUES " ;
	
	String prefix = "INSERT INTO test_1000w (id,NAME,device_second_id,address,refname,device_primary_id,"
			+ "unit_id,decimal_digit,date) VALUES " ;
	try {
	
	// 保存sql后缀
	
	StringBuffer suffix = new StringBuffer();
	
	// 设置事务为非自动提交
	
	conn.setAutoCommit( false );
	
	// 比起st，pst会更好些
	
	PreparedStatement pst = (PreparedStatement) conn.prepareStatement( " " ); //准备执行语句
	
	// 外层循环，总提交事务次数
	
	for ( double i = 1 ; i <= 100 ; i++) {
	
	suffix = new StringBuffer();
	
	// 第j次提交步长
	
	for ( double j = 1 ; j <= 100000 ; j++) {
	
	
	// 构建SQL后缀
//		UUID uuid = UUID.randomUUID();
//		String s = UUID.randomUUID().toString();
//	suffix.append( "('"+i*j+"','空'"+","+"'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+""
//					   		+ "'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"
//					   		+ "'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"
//					   		+ "'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"
//					   		+ "'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空'"+",'空')," );
	
	suffix.append( "('" +i*j+ "','空','空','空','空','空','空','空'"+",'"+"2020-03-25 09:30:25"+"')," );
	}
	
	// 构建完整SQL
	
	String sql = prefix + suffix.substring( 0 , suffix.length() - 1 );
	
	// 添加执行SQL
	
	pst.addBatch(sql);
	
	// 执行操作
	
	pst.executeBatch();
	
	// 提交事务
	
	conn.commit();
	
	// 清空上一次添加的数据
	
	suffix = new StringBuffer();
	
	}
	
	// 头等连接
	
	pst.close();
	
	conn.close();
	
	} catch (SQLException e) {
	
	e.printStackTrace();
	
	}
	
	// 结束时间
	
	Long end = new Date().getTime();
	
	// 耗时
	
	System.out.println( "100万条数据插入花费时间 : " + (end - begin) / 1000 + " s" );
	
	System.out.println( "插入完成" );
	
	}

}
