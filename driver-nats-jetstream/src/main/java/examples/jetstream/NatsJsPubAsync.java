// Copyright 2020 The NATS Authors
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package examples.jetstream;

import io.nats.client.Connection;
import io.nats.client.JetStream;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.api.PublishAck;
import io.nats.client.impl.NatsMessage;
import examples.ExampleArgs;
import examples.ExampleUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This example will demonstrate JetStream async / future publishing.
 */
public class NatsJsPubAsync {
    static final String usageString =
            "\nUsage: java -cp <classpath> NatsJsPubAsync [-s server] [-strm stream] [-sub subject] [-mcnt msgCount] [-m messageWords+] [-r headerKey:headerValue]*"
                    + "\n\nDefault Values:"
                    + "\n   [-strm] example-stream"
                    + "\n   [-sub]  example-subject"
                    + "\n   [-mcnt] 10"
                    + "\n   [-m] hello"
                    + "\n\nRun Notes:"
                    + "\n   - msg_count < 1 is the same as 1"
                    + "\n   - headers are optional"
                    + "\n\nUse tls:// or opentls:// to require tls, via the Default SSLContext\n"
                    + "\nSet the environment variable NATS_NKEY to use challenge response authentication by setting a file containing your private key.\n"
                    + "\nSet the environment variable NATS_CREDS to use JWT/NKey authentication by setting a file containing your user creds.\n"
                    + "\nUse the URL in the -s server parameter for user/pass/token authentication.\n";

    public static void main(String[] args) {
        ExampleArgs exArgs = ExampleArgs.builder("Publish Async", args, usageString)
                .defaultStream("example-stream")
                .defaultSubject("example-subject")
                .defaultMessage("hello")
                .defaultMsgCount(10)
                .build();

        String hdrNote = exArgs.hasHeaders() ? ", with " + exArgs.headers.size() + " header(s)" : "";
        System.out.printf("\nPublishing to %s%s. Server is %s\n\n", exArgs.subject, hdrNote, exArgs.server);

        try (Connection nc = Nats.connect(ExampleUtils.createExampleOptions(exArgs.server))) {

            // Create a JetStream context.  This hangs off the original connection
            // allowing us to produce data to streams and consume data from
            // JetStream consumers.
            JetStream js = nc.jetStream();

            // See the NatsJsManageStreams example or the NatsJsUtils for examples on how to create the stream
            NatsJsUtils.createStreamOrUpdateSubjects(nc, exArgs.stream, exArgs.subject);

            List<CompletableFuture<PublishAck>> futures = new ArrayList<>();

            int stop = exArgs.msgCount < 2 ? 2 : exArgs.msgCount + 1;
            for (int x = 1; x < stop; x++) {
                // make unique message data if you want more than 1 message
                String data = exArgs.msgCount < 2 ? exArgs.message : exArgs.message + "-" + x;

                // create a typical NATS message
                Message msg = NatsMessage.builder()
                        .subject(exArgs.subject)
                        .headers(exArgs.headers)
                        .data(data, StandardCharsets.UTF_8)
                        .build();
                System.out.printf("Publishing message %s on subject %s.\n", data, exArgs.subject);

                // Publish a message
                futures.add(js.publishAsync(msg));
            }

            while (futures.size() > 0) {
                CompletableFuture<PublishAck> f = futures.remove(0);
                if (f.isDone()) {
                    try {
                        PublishAck pa = f.get();
                        System.out.printf("Publish Succeeded on subject %s, stream %s, seqno %d.\n",
                                exArgs.subject, pa.getStream(), pa.getSeqno());
                    }
                    catch (ExecutionException ee) {
                        System.out.println("Publish Failed " + ee);
                    }
                }
                else {
                    // re queue it and try again
                    futures.add(f);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
