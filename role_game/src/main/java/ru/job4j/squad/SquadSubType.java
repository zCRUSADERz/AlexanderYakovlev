package ru.job4j.squad;

public enum SquadSubType {
    MAIN {
        @Override
        public String toString() {
            return "Squad";
        }
    },
    REGULAR {
        @Override
        public String toString() {
            return "Regular";
        }
    },
    UPGRADED {
        @Override
        public String toString() {
            return "Upgraded";
        }
    }
}
