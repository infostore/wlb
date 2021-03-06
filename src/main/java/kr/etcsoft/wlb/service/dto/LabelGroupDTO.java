package kr.etcsoft.wlb.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link kr.etcsoft.wlb.domain.LabelGroup} entity.
 */
public class LabelGroupDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 10)
    private String name;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LabelGroupDTO labelGroupDTO = (LabelGroupDTO) o;
        if (labelGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), labelGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LabelGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
