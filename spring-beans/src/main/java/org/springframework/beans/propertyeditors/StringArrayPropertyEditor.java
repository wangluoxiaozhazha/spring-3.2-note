/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * Custom {@link java.beans.PropertyEditor} for String arrays.
 *
 * <p>Strings must be in CSV format, with a customizable separator.
 * By default values in the result are trimmed of whitespace.
 *
 * 字符串数组编辑器
 * 使用的是csv的格式
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Dave Syer
 * @see org.springframework.util.StringUtils#delimitedListToStringArray
 * @see org.springframework.util.StringUtils#arrayToDelimitedString
 */
public class StringArrayPropertyEditor extends PropertyEditorSupport {

    /**
     * Default separator for splitting a String: a comma (",")
     *
     * 默认的分隔符
     */
    public static final String DEFAULT_SEPARATOR = ",";


    /**
     * 分隔符
     */
    private final String separator;

    /**
     * 删除的字符
     */
    private final String charsToDelete;

    /**
     * 空数组作为null
     */
    private final boolean emptyArrayAsNull;

    /**
     * 去掉的内容
     */
    private final boolean trimValues;


    /**
     * Create a new StringArrayPropertyEditor with the default separator
     * (a comma).
     * <p>An empty text (without elements) will be turned into an empty array.
     *
     * 构造方法
     */
    public StringArrayPropertyEditor() {
        this(DEFAULT_SEPARATOR, null, false);
    }

    /**
     * Create a new StringArrayPropertyEditor with the given separator.
     * <p>An empty text (without elements) will be turned into an empty array.
     *
     * 构造方法
     *
     * @param separator the separator to use for splitting a {@link String}
     */
    public StringArrayPropertyEditor(String separator) {
        this(separator, null, false);
    }

    /**
     * Create a new StringArrayPropertyEditor with the given separator.
     *
     * 构造方法
     *
     * @param separator the separator to use for splitting a {@link String}
     * @param emptyArrayAsNull {@code true} if an empty String array
     * is to be transformed into {@code null}
     */
    public StringArrayPropertyEditor(String separator, boolean emptyArrayAsNull) {
        this(separator, null, emptyArrayAsNull);
    }

    /**
     * Create a new StringArrayPropertyEditor with the given separator.
     *
     * 构造方法
     *
     * @param separator the separator to use for splitting a {@link String}
     * @param emptyArrayAsNull {@code true} if an empty String array
     * is to be transformed into {@code null}
     * @param trimValues {@code true} if the values in the parsed arrays
     * are to be be trimmed of whitespace (default is true).
     */
    public StringArrayPropertyEditor(String separator, boolean emptyArrayAsNull, boolean trimValues) {
        this(separator, null, emptyArrayAsNull, trimValues);
    }

    /**
     * Create a new StringArrayPropertyEditor with the given separator.
     *
     * 构造方法
     *
     * @param separator the separator to use for splitting a {@link String}
     * @param charsToDelete a set of characters to delete, in addition to
     * trimming an input String. Useful for deleting unwanted line breaks:
     * e.g. "\r\n\f" will delete all new lines and line feeds in a String.
     * @param emptyArrayAsNull {@code true} if an empty String array
     * is to be transformed into {@code null}
     */
    public StringArrayPropertyEditor(String separator, String charsToDelete, boolean emptyArrayAsNull) {
        this(separator, charsToDelete, emptyArrayAsNull, true);
    }

    /**
     * Create a new StringArrayPropertyEditor with the given separator.
     *
     * 构造方法
     *
     * @param separator the separator to use for splitting a {@link String}
     * @param charsToDelete a set of characters to delete, in addition to
     * trimming an input String. Useful for deleting unwanted line breaks:
     * e.g. "\r\n\f" will delete all new lines and line feeds in a String.
     * @param emptyArrayAsNull {@code true} if an empty String array
     * is to be transformed into {@code null}
     * @param trimValues {@code true} if the values in the parsed arrays
     * are to be be trimmed of whitespace (default is true).
     */
    public StringArrayPropertyEditor(String separator, String charsToDelete, boolean emptyArrayAsNull, boolean trimValues) {
        this.separator = separator;
        this.charsToDelete = charsToDelete;
        this.emptyArrayAsNull = emptyArrayAsNull;
        this.trimValues = trimValues;
    }

    /**
     * 设置
     *
     * @param text
     * @throws IllegalArgumentException
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] array = StringUtils.delimitedListToStringArray(text, this.separator, this.charsToDelete);
        if (trimValues) {
            array = StringUtils.trimArrayElements(array);
        }
        if (this.emptyArrayAsNull && array.length == 0) {
            setValue(null);
        } else {
            setValue(array);
        }
    }

    /**
     * 获取
     *
     * @return
     */
    @Override
    public String getAsText() {
        return StringUtils.arrayToDelimitedString(ObjectUtils.toObjectArray(getValue()), this.separator);
    }

}
