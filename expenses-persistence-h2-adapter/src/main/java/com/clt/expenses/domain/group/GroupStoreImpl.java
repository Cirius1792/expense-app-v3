package com.clt.expenses.domain.group;

import com.clt.domain.group.Group;
import com.clt.domain.group.GroupNotFound;
import com.clt.domain.group.GroupStore;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class GroupStoreImpl implements GroupStore {
  private final GroupRepository groupRepository;
  private final GroupPersistenceMapper mapper;

  public GroupStoreImpl(GroupRepository groupRepository, GroupPersistenceMapper mapper) {
    this.groupRepository = groupRepository;
    this.mapper = mapper;
  }

  @Override
  public Mono<Group> store(Group group) {
    return Mono.just(group).doOnNext(el -> this.groupRepository.save(mapper.toEntity(group)));
  }

  @Override
  public Mono<Group> retrieve(String groupId) {
    return groupRepository
        .findById(groupId)
        .switchIfEmpty(Mono.error(new GroupNotFound(groupId)))
        .map(mapper::toDomain);
  }

  @Override
  public Flux<Group> retrieveByMember(String userId) {
    return groupRepository.findByMember(userId).map(mapper::toDomain);
  }

  @Override
  public Mono<Void> addMembers(String groupId, List<String> members) {
    return this.groupRepository.addMember(groupId, members);
  }

}
