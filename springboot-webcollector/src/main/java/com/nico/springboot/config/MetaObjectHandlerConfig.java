package com.nico.springboot.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

@Component
public class MetaObjectHandlerConfig extends MetaObjectHandler {

	/**
	 * metaObject 就是实际更新的某个表的某行数据数据
	 * FieldValByName  表字段
	 */
  @Override
  public void insertFill(MetaObject metaObject) {
    System.out.println("插入方法实体填充");
    setFieldValByName("testDate", new Date(), metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    System.out.println("更新方法实体填充");
    setFieldValByName("testDate", new Date(), metaObject);
  }
}
