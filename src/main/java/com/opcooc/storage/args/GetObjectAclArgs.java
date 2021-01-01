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

import static com.opcooc.storage.toolkit.StorageChecker.validateNotEmptyString;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author shenqicheng
 * @since 1.2.2
 */
@Getter
@SuperBuilder(toBuilder = true)
public class GetObjectAclArgs extends BucketArgs {

    private String objectName;

    @Override
    public void validate() {
        super.validate();
        validateNotEmptyString(this.objectName, "object name");
    }
}
