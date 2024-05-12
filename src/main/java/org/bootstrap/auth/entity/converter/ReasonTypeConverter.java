package org.bootstrap.auth.entity.converter;

import org.bootstrap.auth.entity.ReasonType;
import org.bootstrap.auth.utils.EnumCodeAttributeConverter;

public class ReasonTypeConverter extends EnumCodeAttributeConverter<ReasonType> {

    public ReasonTypeConverter() {
        super(ReasonType.class);
    }
}
