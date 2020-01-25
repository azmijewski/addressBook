package com.zmijewski.adam.addressbook.util;

public interface AbstractConventerToDto<T, K> {
     K convert (T object);
}
