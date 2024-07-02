package org.evrete.guides.showcase.town;

import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;
import org.evrete.api.Type;
import org.evrete.guides.showcase.AbstractWebsocketHandler;
import org.evrete.guides.showcase.MessageWriter;
import org.evrete.guides.showcase.ShowcaseUtils;
import org.evrete.guides.showcase.town.dto.Entity;
import org.evrete.guides.showcase.town.dto.GeoData;
import org.evrete.guides.showcase.town.rules.MainRuleset;
import org.evrete.guides.showcase.town.rules.NonWorkingPeople;
import org.evrete.guides.showcase.town.rules.TravelUtils;
import org.evrete.guides.showcase.town.rules.WorkingPeople;

import java.io.IOException;

public class TownWebsocketHandler extends AbstractWebsocketHandler<TownWebsocketSession> {
    private final Knowledge knowledge;
    private final GeoData geoData;

    public TownWebsocketHandler() {
        try {
            KnowledgeService knowledgeService = new KnowledgeService();
            this.geoData = ShowcaseUtils.fromJson(ShowcaseUtils.readResourceAsString("/META-INF/data/town/homes.json"), GeoData.class);
            this.knowledge = knowledgeService
                    .newKnowledge()
                    .configureTypes(typeResolver -> {
                        Type<Entity> type = typeResolver.declare(Entity.class);
                        typeResolver.addType(new Entity.EntityKnowledgeType(type));
                    })
                    .importRules("JAVA-CLASS", new Class<?>[]{
                            MainRuleset.class, WorkingPeople.class, NonWorkingPeople.class, TravelUtils.class
                    });
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected TownWebsocketSession newSession(MessageWriter writer) {

        return new TownWebsocketSession(writer, knowledge, geoData);
    }
}
