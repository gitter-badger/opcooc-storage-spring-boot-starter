/*
 * Copyright © 2020-2029 organization opcooc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opcooc.storage.args;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author shenqicheng
 * @since 1.2.0
 */
@SuperBuilder(toBuilder = true)
public abstract class BaseArgs {

    /**
     * 特殊标记(用于用户对 bucketConverter， objectConverter 的自定义拓展)
     */
    @Getter
    private String sign;

    /**
     * 基础参数检验器
     */
    public abstract void validate();
}
