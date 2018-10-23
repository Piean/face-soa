package com.mogu.demo.mapper;

import com.mogu.demo.face.bo.Group;

public interface GroupMapper {
    int insertGroup(Group group);
    Group getByEntityId(String entityId);
}
