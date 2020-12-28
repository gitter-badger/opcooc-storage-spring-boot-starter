/*
 * Copyright © 2020-2025 organization opcooc
 * <pre>
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
 * <pre/>
 */
package com.opcooc.storage.args;

import com.opcooc.storage.exception.StorageException;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import static com.opcooc.storage.toolkit.StorageChecker.validateNotEmptyString;

/**
 * @author shenqicheng
 * @since 1.2.0
 */
@Slf4j
@Getter
@SuperBuilder(toBuilder = true)
public class SetFolderArgs extends BucketArgs {

    private static final String SYMBOL = "/";

    private String folderName;

    @Override
    public void validate() {
        super.validate();
        validateFolderName(folderName);
    }

    private void validateFolderName(String folderName) {

        validateNotEmptyString(folderName, "folderName");

        if (!folderName.endsWith(SYMBOL)) {
            throw new StorageException("opcooc-storage - [%s] folderName must end with '/' ", folderName);
        }
    }
}
