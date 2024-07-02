package com.first.decorator;

import com.first.enums.Fields;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort.Direction;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortDto {
    Fields field;
    Fields field2;
    Direction direction;
}
