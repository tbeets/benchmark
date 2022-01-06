/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.openmessaging.benchmark.driver.natsJetStream;

import io.nats.client.Connection;
import io.nats.client.JetStream;
import io.openmessaging.benchmark.driver.BenchmarkConsumer;

import java.time.Duration;

public class NatsBenchmarkConsumer implements BenchmarkConsumer {
    // the con has-a dispatcher, the dispatcher has-a subscription
    private Connection con;
    private JetStream js;

    public NatsBenchmarkConsumer (Connection con, JetStream js) {
        this.con = con;
        this.js = js;
    }

    @Override public void close() throws Exception {
        con.drain(Duration.ofSeconds(10));
        con.close();
    }
}
