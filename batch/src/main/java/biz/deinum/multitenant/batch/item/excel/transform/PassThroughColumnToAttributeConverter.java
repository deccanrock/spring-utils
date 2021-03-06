/*
 * Copyright 2011 the original author or authors.
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
 */package biz.deinum.multitenant.batch.item.excel.transform;

/**
 * {@link ColumnToAttributeConverter} which simply returns the given value.
 * 
 * @author Marten Deinum
 */
public class PassThroughColumnToAttributeConverter implements ColumnToAttributeConverter {

    public String toAttribute(final String column) {
        return column;
    }

    public String toColumn(final String attribute) {
        return attribute;
    }

}
