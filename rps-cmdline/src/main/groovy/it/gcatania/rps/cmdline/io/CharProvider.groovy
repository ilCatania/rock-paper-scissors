/**
 *
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