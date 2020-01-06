package io.github.ldw5821cn.function;

/**
 * 转换器接口，用于将 T 转换为 U
 * @param <T> 原始类型
 * @param <U> 目标类型
 */
public interface Function<T,U> {
    U apply(T t);
}
