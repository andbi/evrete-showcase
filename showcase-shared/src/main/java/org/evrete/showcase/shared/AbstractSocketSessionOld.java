package org.evrete.showcase.shared;

import org.evrete.api.StatefulSession;

import javax.websocket.Session;

public abstract class AbstractSocketSessionOld {
    private final SocketMessenger messenger;
    private StatefulSession knowledgeSession;

    public AbstractSocketSessionOld(Session session) {
        this.messenger = new SocketMessenger(session);
        session.setMaxTextMessageBufferSize(1024 * 1024);
        session.setMaxIdleTimeout(1000 * 60 * 30);
    }

    public final SocketMessenger getMessenger() {
        return messenger;
    }

    public final StatefulSession getKnowledgeSession() {
        return knowledgeSession;
    }

    public final void setKnowledgeSession(StatefulSession knowledgeSession) {
        this.knowledgeSession = knowledgeSession;
    }

    public void process(String message) throws Exception {
        throw new UnsupportedOperationException();
    }

    public boolean closeSession() {
        if (knowledgeSession != null) {
            knowledgeSession.close();
            knowledgeSession = null;
            return true;
        } else {
            return false;
        }
    }
}
