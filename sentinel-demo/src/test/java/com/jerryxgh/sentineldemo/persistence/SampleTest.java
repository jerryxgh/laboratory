package com.jerryxgh.sentineldemo.persistence;

import com.alibaba.fastjson.JSON;
import com.jerryxgh.sentineldemo.persistence.mybatisplus.mapper.MybatisPlusUserMapper;
import com.jerryxgh.sentineldemo.persistence.mybatisplus.model.UserDO;
import com.jerryxgh.sentineldemo.persistence.tkmapper.mapper.TkMapperUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Autowired
    private MybatisPlusUserMapper mybatisPlusUserMapper;

    @Autowired
    private TkMapperUserMapper tkMapperUserMapper;

    @Test
    public void testMybatisPlusSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<UserDO> userList = mybatisPlusUserMapper.selectList(null);
        userList.forEach(o-> System.out.println(JSON.toJSONString(o)));
    }

    @Test
    public void testInsert() {
        UserDO userDO = mybatisPlusUserMapper.selectById(1);
        System.out.println(JSON.toJSONString(userDO));

        userDO.setId(100L);
        userDO.setGmtCreate(new Date());
        userDO.setGmtModified(new Date());
        mybatisPlusUserMapper.insert(userDO);
    }

    @Test
    public void testTkMapperSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<com.jerryxgh.sentineldemo.persistence.tkmapper.model.UserDO> userList = tkMapperUserMapper.selectAll();
        userList.forEach(o-> System.out.println(JSON.toJSONString(o)));
    }

    @Test
    public void testTkMapperInsert() {
        com.jerryxgh.sentineldemo.persistence.tkmapper.model.UserDO userDO = tkMapperUserMapper.selectByPrimaryKey(1L);
        System.out.println(JSON.toJSONString(userDO));

        userDO.setId(108L);
        userDO.setGmtCreate(new Date());
        userDO.setGmtModified(new Date());
        tkMapperUserMapper.insert(userDO);
//        tkMapperUserMapper.updateByExampleSelective()
    }
}
