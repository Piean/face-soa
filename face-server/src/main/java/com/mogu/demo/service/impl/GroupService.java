package com.mogu.demo.service.impl;

import com.mogu.demo.face.bo.Group;
import com.mogu.demo.mapper.GroupMapper;
import com.mogu.demo.service.IGroupService;
import com.mogu.demo.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class GroupService implements IGroupService {
    private static final Map<String,String> ID_CACHE = new HashMap<>();

    @Resource
    private GroupMapper groupMapper;

    @Override
    public Group createGroup(Group group) {
        if (StringUtils.isAnyBlank(group.getEntityId(),group.getGroupName())) {
            return null;
        }
        if (StringUtils.isBlank(group.getId())) {
            group.setId(StringUtil.getUUID());
        }
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

    @Override
    public String getGroupId(String entityId) {
        String groupId = ID_CACHE.get(entityId);
        if (groupId != null) {
            return groupId;
        }

        Group group = this.getByEntityId(entityId);
        if (group != null) {
            ID_CACHE.put(entityId,group.getId());
            return group.getId();
        }
        return null;
    }
}
