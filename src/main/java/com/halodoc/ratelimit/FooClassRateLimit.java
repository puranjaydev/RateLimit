package com.halodoc.ratelimit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FooClassRateLimit {

    Long start;
    Long end;
    Integer noOfrequest;
}
