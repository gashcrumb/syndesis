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
package io.syndesis.connector.aws.sns;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.camel.CamelContext;
import org.apache.camel.component.extension.MetaDataExtension;
import org.apache.camel.impl.DefaultCamelContext;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled("Requires access to AWS account")
public class AWSSNSMetaDataExtensionTest {

    @Test
    public void retrieveQueuesListTest() {
        CamelContext context = new DefaultCamelContext();
        AWSSNSMetaDataExtension extension = new AWSSNSMetaDataExtension(context);

        Map<String, Object> properties = new HashMap<>();
        properties.put("accessKey", "accessKey");
        properties.put("secretKey", "secretKey");
        properties.put("region", "eu-central-1");

        Optional<MetaDataExtension.MetaData> meta = extension.meta(properties);

        assertThat(meta).isPresent();
        assertThat(meta.get().getPayload()).isInstanceOf(HashSet.class);
        assertThat(meta.get().getAttributes()).hasEntrySatisfying(MetaDataExtension.MetaData.JAVA_TYPE, new Condition<Object>() {
            @Override
            public boolean matches(Object val) {
                return Objects.equals(String.class, val);
            }
        });
        assertThat(meta.get().getAttributes()).hasEntrySatisfying(MetaDataExtension.MetaData.CONTENT_TYPE, new Condition<Object>() {
            @Override
            public boolean matches(Object val) {
                return Objects.equals("text/plain", val);
            }
        });
    }

    @Test
    public void noChannelTest() {
        CamelContext context = new DefaultCamelContext();
        AWSSNSMetaDataExtension extension = new AWSSNSMetaDataExtension(context);

        Map<String, Object> properties = new HashMap<>();
        properties.put("webhookurl", "token");

        Optional<MetaDataExtension.MetaData> meta = extension.meta(properties);

        assertThat(meta).isEmpty();
    }
}
