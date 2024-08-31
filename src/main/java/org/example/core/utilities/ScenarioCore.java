package org.example.core.utilities;

import java.util.Optional;
import java.util.function.Supplier;

public class ScenarioCore {
    protected boolean needsDefaultValuesPopulated = true;

    protected <T> T getNonNull(T itemDefault, T itemOptional) {
        return Optional.ofNullable(itemDefault).orElse(itemOptional);
    }

    protected <T> T getNonNull(T itemDefault, Supplier<T> supplier) {
        if (itemDefault != null) {
            return itemDefault;
        } else {
            if (supplier == null) {
                return null;
            }
            return supplier.get();
        }
    }
}
