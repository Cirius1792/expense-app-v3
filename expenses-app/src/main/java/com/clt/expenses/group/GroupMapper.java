package com.clt.expenses.group;

import com.clt.domain.group.Group;
import com.clt.expenses.group.response.GroupResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GroupMapper {
    public GroupResponse toDto(Mono<Group> groupMono) {
        return null;
    }
}
