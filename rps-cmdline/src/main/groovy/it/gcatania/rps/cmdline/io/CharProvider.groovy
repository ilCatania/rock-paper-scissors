/*
 * Copyright 2022 Gabriele Catania <gabriele.ctn@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.gcatania.rps.cmdline.io

import groovy.util.logging.Slf4j
import jline.console.ConsoleReader


/**
 * @author ilCatania
 */
@Slf4j
class CharProvider {

    private final ConsoleReader r
    private final List<CharListener> listeners = []

    private Thread pollerThread
    private volatile boolean pollingEnabled = false

    CharProvider(ConsoleReader r) {
        this.r = r
    }

    void addListener(CharListener l) {
        listeners.add l
    }
    void removeListener(CharListener l) {
        listeners.remove l
    }

    // FIXME this polling implementation always eats one more
    // character even after the polling has been stopped and
    // all listeners deregistered
    void startPolling(long pollFrequencyMillis) {
        pollingEnabled = true
        pollerThread = Thread.start('charProviderPoller', {
            while(pollingEnabled) {
                int c = r.readCharacter()
                if(c != -1) {
                    listeners*.charReceived((char)c)
                }
                Thread.sleep(pollFrequencyMillis)
            }
            log.trace 'polling stopped'
        })
        log.trace 'polling started'
    }

    void stopPolling() {
        pollingEnabled = false
        log.trace 'polling stop requested'
    }

    // FIXME dirty trick to wait for the poller to actually finish
    void joinPollerThread() { pollerThread.join() }
}