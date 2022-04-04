package com.techvg.inventory.management.service.mapper;

import com.techvg.inventory.management.domain.Project;
import com.techvg.inventory.management.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Named("projectName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "projectName", source = "projectName")
    ProjectDTO toDtoProjectName(Project project);
}
