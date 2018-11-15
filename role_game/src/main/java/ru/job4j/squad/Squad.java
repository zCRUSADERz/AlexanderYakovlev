package ru.job4j.squad;

import java.util.Map;

public class Squad {
    private final String squadName;
    private final Map<SquadSubType, SquadOperation> operationsMap;

    public Squad(String squadName, Map<SquadSubType, SquadOperation> operationsMap) {
        this.squadName = squadName;
        this.operationsMap = operationsMap;
    }

    public SquadOperation operation(SquadSubType type) {
        return this.operationsMap.get(type);
    }

    @Override
    public String toString() {
        return this.squadName;
    }
}
