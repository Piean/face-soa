package com.mogu.demo.service;

import com.mogu.demo.face.bo.Group;

public interface IGroupService {
    Group createGroup(Group group);
    Group getByEntityId(String entityId);
}
