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
package com.opcooc.storage.exception;

/**
 * @author shenqicheng
 * @since 1.2.0
 */
public class StorageException extends RuntimeException {

    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(Exception e) {
        this("method: [%s] error message: [%s]", e.getClass().getSimpleName(), e.getMessage());
    }

    public StorageException(String format, Object... args) {
        this(String.format(format, args));
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageException(String format, Throwable cause, Object... args) {
        this(String.format(format, args), cause);
    }

}
