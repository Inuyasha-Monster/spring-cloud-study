package com.djl.cloud.config;

import org.springframework.core.NamedThreadLocal;

public class RibbonRequestContextHolder {
    private final static ThreadLocal<RibbonRequestContext> CONTEXT_HOLDER =
            new NamedThreadLocal<RibbonRequestContext>("Ribbon Request Context Holder") {
                @Override
                protected RibbonRequestContext initialValue() {
                    return new RibbonRequestContext();
                }
            };

    public static RibbonRequestContext get() {
        return CONTEXT_HOLDER.get();
    }

    public static void set(RibbonRequestContext context) {
        CONTEXT_HOLDER.set(context);
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
