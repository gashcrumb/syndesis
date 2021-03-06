/*
 * Copyright (C) 2016 Red Hat, Inc.
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
package io.syndesis.common.model.bulletin;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.syndesis.common.model.Kind;
import io.syndesis.common.model.WithId;
import io.syndesis.common.util.IndexedProperty;
import org.immutables.value.Value;

/**
 * A ConnectionBulletinBoard holds any notifications that should be displayed to the user
 * for a given connection.
 */
@Value.Immutable
@JsonDeserialize(builder = ConnectionBulletinBoard.Builder.class)
@SuppressWarnings("immutables")
@IndexedProperty("targetResourceId")
public interface ConnectionBulletinBoard extends WithId<ConnectionBulletinBoard>, BulletinBoard<ConnectionBulletinBoard> {

    @Override
    default Kind getKind() {
        return Kind.ConnectionBulletinBoard;
    }

    static ConnectionBulletinBoard emptyBoard() {
        return new ConnectionBulletinBoard.Builder().build();
    }

    static ConnectionBulletinBoard of(String id, List<LeveledMessage> messages) {
        return new ConnectionBulletinBoard.Builder().id(id).targetResourceId(id).messages(messages).build();
    }

    class Builder extends ImmutableConnectionBulletinBoard.Builder {
        // make ImmutableConnectionBulletinBoard.Builder which is package
        // private accessible
    }
}
