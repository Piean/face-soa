package com.mogu.demo.service.impl;

import com.mogu.demo.face.bo.Group;
import com.mogu.demo.mapper.GroupMapper;
import com.mogu.demo.service.IGroupService;
import com.mogu.demo.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GroupService implements IGroupService {
    @Resource
    private GroupMapper groupMapper;

    @Override
    public Group createGroup(Group group) {
        if (StringUtils.isAnyBlank(group.getEntityId(),group.getGroupName())) {
            return null;
        }
        group.setId(StringUtil.getUUID());
        groupMapper.insertGroup(group);
        return group;
    }

    @Override
    public Group getByEntityId(String entityId) {
        if (StringUtils.isBlank(entityId)) {
            return null;
        }
        return groupMapper.getByEntityId(entityId);
    }
}
